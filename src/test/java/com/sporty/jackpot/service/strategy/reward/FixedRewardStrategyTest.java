package com.sporty.jackpot.service.strategy.reward;

import com.sporty.jackpot.service.model.entity.Jackpot;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author raymond
 */
public class FixedRewardStrategyTest {
    
    private final FixedRewardStrategy fixedStrategy = new FixedRewardStrategy();
    
    private Jackpot createJackpot(BigDecimal pool) {
        Jackpot j = new Jackpot();
        j.setCurrentPool(pool);
        j.setRewardThreshold(new BigDecimal("1000.00"));
        return j;
    }

    @Test
    void fixedStrategy_WhenPoolIsThreshold_ShouldReturnTrue() {
        Jackpot jackpot = new Jackpot();
        jackpot.setCurrentPool(new BigDecimal("1000.00")); 
        jackpot.setRewardThreshold(new BigDecimal("1000.00")); 
        boolean result = fixedStrategy.isWinner(BigDecimal.TEN, jackpot);

        assertTrue(result);
    }

    @Test
    @DisplayName("Fixed Strategy: Should lose when pool is below threshold")
    void fixedStrategy_WhenPoolBelowThreshold_ShouldReturnFalse() {
        BigDecimal pool = new BigDecimal("999.99");
        BigDecimal stake = new BigDecimal("50.00");
        Jackpot jackpot = createJackpot(pool);

        assertThat(fixedStrategy.isWinner(stake, jackpot)).isFalse();
    }

    @Test
    @DisplayName("Fixed Strategy: Should handle large amounts correctly")
    void fixedStrategy_WhenPoolIsHuge_ShouldReturnTrue() {
        BigDecimal pool = new BigDecimal("1000000.00");
        BigDecimal stake = new BigDecimal("1.00");
        Jackpot jackpot = createJackpot(pool);

        assertThat(fixedStrategy.isWinner(stake, jackpot)).isTrue();
    }
    
}
