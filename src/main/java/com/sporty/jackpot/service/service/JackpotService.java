package com.sporty.jackpot.service.service;

import com.sporty.jackpot.service.model.dto.BetRequest;
import com.sporty.jackpot.service.model.dto.RewardResponse;
import com.sporty.jackpot.service.model.entity.Jackpot;
import com.sporty.jackpot.service.model.entity.JackpotContribution;
import com.sporty.jackpot.service.model.entity.JackpotReward;
import com.sporty.jackpot.service.repository.JackpotContributionRepository;
import com.sporty.jackpot.service.repository.JackpotRepository;
import com.sporty.jackpot.service.repository.JackpotRewardRepository;
import com.sporty.jackpot.service.strategy.StrategyFactory;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 *
 * @author raymond
 */
@Service
public class JackpotService {
    
    private static final Logger log = LoggerFactory.getLogger(JackpotService.class);

    
    private final JackpotRepository jackpotRepository;
    private final JackpotContributionRepository contributionRepository;
    private final JackpotRewardRepository rewardRepository;
    private final StrategyFactory strategyFactory;

    public JackpotService(JackpotRepository jackpotRepo,
            JackpotContributionRepository contribRepo,
            JackpotRewardRepository rewardRepo,
            StrategyFactory strategyFactory) {
        this.jackpotRepository = jackpotRepo;
        this.rewardRepository = rewardRepo;
        this.contributionRepository = contribRepo;
        this.strategyFactory = strategyFactory;
    }

    @Transactional
    @Retryable(
            retryFor = {OptimisticLockingFailureException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 100)
    )
    public void processContribution(String jackpotId, String betId, String userId, BigDecimal stakeAmount) {
        log.info("Processing Bet: jackpotId={}, betId={}, userId={}", jackpotId, betId, userId);
        
        if (stakeAmount == null || stakeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Contribution must be a positive amount!");
        }
        
        // 1. Fetch the jackpot (lock row)
        Jackpot jackpot = jackpotRepository.findById(jackpotId)
                .orElseThrow(() -> new RuntimeException("Jackpot not found"));

        // 2. Calculate contribution
        BigDecimal contributionAmount = strategyFactory.getContribution(jackpot.getContributionType())
                .calculate(stakeAmount, jackpot); 

        // 3. Update Jackpot Pool
        jackpot.setCurrentPool(jackpot.getCurrentPool().add(contributionAmount));

        // 4. Save Contribution Record
        JackpotContribution contribution = new JackpotContribution();
        contribution.setBetId(betId);
        contribution.setUserId(userId);
        contribution.setJackpotId(jackpotId);
        contribution.setStakeAmount(stakeAmount);
        contribution.setContributionAmount(contributionAmount);
        contribution.setCurrentJackpotAmount(jackpot.getCurrentPool());
        contribution.setCreatedAt(LocalDateTime.now());

        contributionRepository.save(contribution);
    }
    
    @Transactional
    @Retryable
    public RewardResponse evaluateAndReward(String jackpotId, BetRequest bet) {
        Jackpot jackpot = jackpotRepository.findById(jackpotId)
                .orElseThrow(() -> new IllegalArgumentException("Jackpot not found: " + jackpotId));

        // 1. Evaluate logic
        boolean isWinner = strategyFactory.getReward(jackpot.getRewardType())
                .isWinner(bet.amount(), jackpot);

        if (isWinner) {
            // 2. Persist reward, reset pool, and return response
            BigDecimal prize = jackpot.getCurrentPool();
            String userId = bet.userId();
            
            log.info("JACKPOT_WON: jackpotId={}, betId={}, userId={}, amount={}",
                    jackpotId, bet.betId(), userId, prize);

            // Save Reward Record
            JackpotReward reward = new JackpotReward();
            reward.setBetId(bet.betId());
            reward.setUserId(bet.userId());
            reward.setJackpotId(jackpotId);
            reward.setRewardAmount(jackpot.getCurrentPool());
            reward.setCreatedAt(LocalDateTime.now());
            rewardRepository.save(reward);

            // Reset Jackpot
            jackpot.setCurrentPool(jackpot.getInitialPool());
            jackpotRepository.save(jackpot);

            return new RewardResponse(true, prize, "Winner!");
        }

        return new RewardResponse(false, BigDecimal.ZERO, "Better luck next time.");
    }
}
