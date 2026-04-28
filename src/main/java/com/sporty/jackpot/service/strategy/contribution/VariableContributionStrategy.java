package com.sporty.jackpot.service.strategy.contribution;

import com.sporty.jackpot.service.model.entity.Jackpot;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

/**
 *
 * @author raymond (ray.a.ralph@gmail.com)
 */
@Component
public class VariableContributionStrategy implements ContributionStrategy {
    

    public static final String TYPE = "VARIABLE";
    private static final int SCALE = 4; // Align with Database precision

    @Override
    public BigDecimal calculate(BigDecimal stakeAmount, Jackpot jackpot) {
        // Defensive check
        if (stakeAmount == null || jackpot.getContributionRate() == null) {
            return BigDecimal.ZERO.setScale(SCALE, RoundingMode.HALF_UP);
        }

        BigDecimal baseRate = jackpot.getContributionRate();
        BigDecimal currentPool = jackpot.getCurrentPool();
        BigDecimal decayRate = jackpot.getDecayRate() != null ? jackpot.getDecayRate() : BigDecimal.ZERO;

        // Formula: CurrentRate = BaseRate - (CurrentPool * DecayRate)
        // We calculate this without premature rounding to maintain accuracy
        BigDecimal decayAmount = currentPool.multiply(decayRate);
        BigDecimal currentRate = baseRate.subtract(decayAmount);

        // Enforce the floor (0.00) so we never have a negative contribution
        BigDecimal effectiveRate = currentRate.max(BigDecimal.ZERO);

        // Calculate final contribution and scale to 4 (matching DB entity)
        return stakeAmount.multiply(effectiveRate)
                .setScale(SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public String getType() {
        return TYPE;
    }
    

}
