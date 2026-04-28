package com.sporty.jackpot.service.strategy.reward;

import com.sporty.jackpot.service.model.entity.Jackpot;
import java.math.BigDecimal;
import java.security.SecureRandom;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author raymond
 */
public class VariableRewardStrategyTest {
    
    private Jackpot createJackpot(BigDecimal pool) {
        Jackpot j = new Jackpot();
        j.setCurrentPool(pool);
        j.setRewardThreshold(new BigDecimal("1000.00"));
        return j;
    }


    @Test
    @DisplayName("Variable Strategy: Should win when random value is lower than stake/pool ratio")
    void variableStrategy_ShouldWin() {
        SecureRandom seededRandom = new SecureRandom();
        seededRandom.setSeed(1);
        VariableRewardStrategy strategy = new VariableRewardStrategy(seededRandom);

        BigDecimal pool = new BigDecimal("100.00");
        BigDecimal stake = new BigDecimal("80.00"); // Ratio 0.80
        Jackpot jackpot = createJackpot(pool);

        // 0.80 > 0.73, so it should be a winner
        assertThat(strategy.isWinner(stake, jackpot)).isTrue();
    }

    @Test
    @DisplayName("Variable Strategy: Should lose when random value is higher than stake/pool ratio")
    void variableStrategy_ShouldLose() {
        SecureRandom seededRandom = new SecureRandom();
        seededRandom.setSeed(1);
        VariableRewardStrategy strategy = new VariableRewardStrategy(seededRandom);

        BigDecimal pool = new BigDecimal("100.00");
        BigDecimal stake = new BigDecimal("10.00"); // Ratio 0.10
        Jackpot jackpot = createJackpot(pool);

        // 0.10 < 0.73, so it should be a loser
        assertThat(strategy.isWinner(stake, jackpot)).isFalse();
    }
}
