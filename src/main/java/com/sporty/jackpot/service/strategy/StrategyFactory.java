package com.sporty.jackpot.service.strategy;

import com.sporty.jackpot.service.strategy.contribution.ContributionStrategy;
import com.sporty.jackpot.service.strategy.reward.RewardStrategy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.toMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author raymond (ray.a.ralph@gmail.com)
 */
@Component
public class StrategyFactory {

    private static final Logger log = LoggerFactory.getLogger(StrategyFactory.class);
    
    private final Map<String, ContributionStrategy> contributionMap;
    private final Map<String, RewardStrategy> rewardMap;

    public StrategyFactory(List<ContributionStrategy> cStrategies, List<RewardStrategy> rStrategies) {
        this.contributionMap = cStrategies.stream()
                .collect(toMap(
                        ContributionStrategy::getType,
                        s -> s,
                        (existing, replacement) -> {
                            log.error("Duplicate Strategy detected: {}", existing.getType());
                            return existing; // Keep the existing one or throw custom exception
                        }
                ));
        this.rewardMap = rStrategies.stream()
                .collect(toMap(
                        RewardStrategy::getType, 
                        s -> s,
                        (existing, replacement) -> {
                            log.error("Duplicate Strategy detected: {}", existing.getType());
                            return existing; // Keep the existing one or throw custom exception
                        }
                ));
    }

    public ContributionStrategy getContribution(String type) {
        return Optional.ofNullable(contributionMap.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Unknown Contribution Strategy: " + type));
    }

    public RewardStrategy getReward(String type) {
        return Optional.ofNullable(rewardMap.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Unknown Reward Strategy: " + type));
    }
}
