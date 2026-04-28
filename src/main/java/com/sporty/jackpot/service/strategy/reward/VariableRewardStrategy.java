package com.sporty.jackpot.service.strategy.reward;

import com.sporty.jackpot.service.model.entity.Jackpot;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

/**
 *
 * @author raymond
 */
@Component
public class VariableRewardStrategy implements RewardStrategy{
    
    public static final String TYPE = "VARIABLE";
    private final SecureRandom random; // Cryptographically strong for gambling

    public VariableRewardStrategy() {
        this.random = new SecureRandom();
    }

    // Constructor for testing
    public VariableRewardStrategy(SecureRandom random) {
        this.random = random;
    }

    @Override
    public boolean isWinner(BigDecimal stake, Jackpot jackpot) {
        BigDecimal pool = jackpot.getCurrentPool();

        // Safety: Prevent DivisionByZero and logical errors
        if (pool == null || pool.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        BigDecimal winProbability = calculateProbability(stake, jackpot);

        // SecureRandom generates a double between 0.0 and 1.0
        return random.nextDouble() < winProbability.doubleValue();
    }

    private BigDecimal calculateProbability(BigDecimal stake, Jackpot jackpot) {
        return stake.divide(jackpot.getCurrentPool(), 4, RoundingMode.HALF_UP);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
