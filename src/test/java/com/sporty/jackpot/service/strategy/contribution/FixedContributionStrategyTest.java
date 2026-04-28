package com.sporty.jackpot.service.strategy.contribution;

import com.sporty.jackpot.service.model.entity.Jackpot;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 *
 * @author raymond
 */
public class FixedContributionStrategyTest {
    
    private final FixedContributionStrategy strategy = new FixedContributionStrategy();

    @Test
    void shouldCalculateFixedPercentage() {
        Jackpot jackpot = new Jackpot();
        jackpot.setContributionRate(new BigDecimal("0.10")); // 10%
        BigDecimal stake = new BigDecimal("50.00");

        BigDecimal result = strategy.calculate(stake, jackpot);

        assertThat(result).isEqualByComparingTo("5.00");
    }
}
