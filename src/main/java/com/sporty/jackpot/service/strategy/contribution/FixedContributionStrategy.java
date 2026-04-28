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
public class FixedContributionStrategy implements ContributionStrategy {

    public static final String TYPE = "FIXED";

    @Override
    public BigDecimal calculate(BigDecimal stakeAmount, Jackpot jackpot) {
        // Defensive check: ensure we don't return null
        if (stakeAmount == null || jackpot.getContributionRate() == null) {
            return BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP);
        }

        // Perform calculation with explicit rounding
        return stakeAmount.multiply(jackpot.getContributionRate())
                .setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
