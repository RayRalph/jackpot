package com.sporty.jackpot.service.strategy.contribution;

import com.sporty.jackpot.service.model.entity.Jackpot;
import java.math.BigDecimal;

/**
 *
 * @author raymond
 */
public interface ContributionStrategy {
    
    BigDecimal calculate(BigDecimal stakeAmount, Jackpot jackpot);

    String getType(); // e.g., "FIXED", "VARIABLE"
}
