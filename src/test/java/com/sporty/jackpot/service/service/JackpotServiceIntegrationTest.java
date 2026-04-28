package com.sporty.jackpot.service.service;

import com.sporty.jackpot.service.model.dto.BetRequest;
import com.sporty.jackpot.service.model.dto.RewardResponse;
import com.sporty.jackpot.service.model.entity.Jackpot;
import com.sporty.jackpot.service.repository.JackpotRepository;
import com.sporty.jackpot.service.repository.JackpotRewardRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author raymond
 */
@SpringBootTest
@Transactional 
public class JackpotServiceIntegrationTest {
    
    @Autowired
    private JackpotService jackpotService;

    @Autowired
    private JackpotRepository jackpotRepository;

    @Autowired
    private JackpotRewardRepository rewardRepository;
    
    @Autowired
    private EntityManager entityManager; 

    @BeforeEach
    void setUp() {
        entityManager.flush();

        jackpotRepository.deleteAllInBatch();

        entityManager.clear();

        Jackpot jackpot = new Jackpot();
        jackpot.setJackpotId("J1");
        jackpot.setCurrentPool(new BigDecimal("1000.00"));
        jackpot.setInitialPool(new BigDecimal("100.00"));
        jackpot.setRewardThreshold(new BigDecimal("1000.00")); 
        jackpot.setRewardType("FIXED");
        jackpot.setContributionType("FIXED");
        jackpot.setContributionRate(new BigDecimal("0.10"));

        jackpotRepository.save(jackpot);

        entityManager.flush();
    }
    
    @Test
    @DisplayName("Service: Should save reward and reset pool on win")
    void shouldProcessWinAndResetPool() {
        BetRequest bet = new BetRequest("J1", "B-101", "user-1", new BigDecimal("50.00"));

        RewardResponse response = jackpotService.evaluateAndReward("J1", bet);

        assertThat(response.isWinner()).isTrue();
        assertThat(response.rewardAmount()).isEqualByComparingTo("1000.00");

        Jackpot updatedJackpot = jackpotRepository.findById("J1").orElseThrow();
        assertThat(updatedJackpot.getCurrentPool()).isEqualByComparingTo("100.00"); // Reset to initial

        assertThat(rewardRepository.count()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Service: Should not save reward and not reset pool on loss")
    void shouldNotProcessWinOnLoss() {
        Jackpot jackpot = jackpotRepository.findById("J1").orElseThrow();
        jackpot.setCurrentPool(new BigDecimal("500.00"));
        jackpotRepository.save(jackpot);
        entityManager.flush(); 

        BetRequest bet = new BetRequest("J1", "B-102", "user-2", new BigDecimal("1.00"));

        RewardResponse response = jackpotService.evaluateAndReward("J1", bet);

        assertThat(response.isWinner()).isFalse();
        assertThat(rewardRepository.count()).isEqualTo(0);
    }
    
    @Test
    @DisplayName("Service: Should throw exception for non-existent jackpot")
    void shouldThrowExceptionForInvalidJackpot() {
        BetRequest bet = new BetRequest("MISSING-ID", "B-999", "user-3", new BigDecimal("10.00"));

        assertThatThrownBy(() -> jackpotService.evaluateAndReward("MISSING-ID", bet))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Jackpot not found");
    }
    
}
