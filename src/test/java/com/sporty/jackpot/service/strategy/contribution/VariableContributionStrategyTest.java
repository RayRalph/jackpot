package com.sporty.jackpot.service.strategy.contribution;

import com.sporty.jackpot.service.model.entity.Jackpot;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 *
 * @author raymond
 */
public class VariableContributionStrategyTest {
    
    private final VariableContributionStrategy strategy = new VariableContributionStrategy();

    private Jackpot createJackpot(String pool, String rate, String decay) {
        Jackpot j = new Jackpot();
        j.setCurrentPool(new BigDecimal(pool));
        j.setContributionRate(new BigDecimal(rate));
        j.setDecayRate(new BigDecimal(decay));
        return j;
    }

    @Test
    void testStandardDecay() {
        // Base 0.20 - (100 * 0.0001) = 0.19
        Jackpot j = createJackpot("100.00", "0.20", "0.0001");
        BigDecimal result = strategy.calculate(new BigDecimal("100.00"), j);
        assertThat(result).isEqualByComparingTo("19.00");
    }
    
    @Test
    void testConfigDrivenDecay() {
        // Jackpot 1: Aggressive decay
        Jackpot aggressive = createJackpot("100.00", "0.20", "0.0010");

        // Jackpot 2: Conservative decay
        Jackpot conservative = createJackpot("100.00", "0.20", "0.0001");

        BigDecimal result1 = strategy.calculate(new BigDecimal("100.00"), aggressive);
        BigDecimal result2 = strategy.calculate(new BigDecimal("100.00"), conservative);

        assertThat(result1).isLessThan(result2); // Aggressive should contribute less
    }

    @Test
    void testBoundaryAtExactlyZero() {
        // Base 0.10 - (1000 * 0.0001) = 0.00
        Jackpot j = createJackpot("1000.00", "0.10", "0.0001");
        BigDecimal result = strategy.calculate(new BigDecimal("100.00"), j);
        assertThat(result).isEqualByComparingTo("0.00");
    }

    @Test
    void testNegativeRatePrevention() {
        // Base 0.10 - (5000 * 0.0001) = 0.10 - 0.50 = -0.40
        // Strategy must cap at 0.00
        Jackpot j = createJackpot("5000.00", "0.10", "0.0001");
        BigDecimal result = strategy.calculate(new BigDecimal("100.00"), j);
        assertThat(result).isEqualByComparingTo("0.00");
    }
}
