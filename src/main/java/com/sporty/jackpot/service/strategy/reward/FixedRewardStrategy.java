package com.sporty.jackpot.service.strategy.reward;

import com.sporty.jackpot.service.model.entity.Jackpot;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 *
 * @author raymond
 */
@Component
public class FixedRewardStrategy implements RewardStrategy{
    public static final String TYPE = "FIXED";

    @Override
    @SuppressWarnings("unused")
    public boolean isWinner(BigDecimal stake, Jackpot jackpot) {
        BigDecimal threshold = jackpot.getRewardThreshold();

        if (threshold == null) {
            return false;
        }

        return jackpot.getCurrentPool().compareTo(threshold) >= 0;
    }

    @Override
    public String getType() {
        return TYPE;
    }
    
}
