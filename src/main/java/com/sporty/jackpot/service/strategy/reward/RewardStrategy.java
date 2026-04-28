package com.sporty.jackpot.service.strategy.reward;

import com.sporty.jackpot.service.model.entity.Jackpot;
import java.math.BigDecimal;

/**
 *
 * @author raymond
 */
public interface RewardStrategy {
    
    boolean isWinner(BigDecimal stake, Jackpot jackpot);
    
    String getType(); // e.g., "FIXED", "VARIABLE"
}
