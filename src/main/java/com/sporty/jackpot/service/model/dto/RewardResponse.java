package com.sporty.jackpot.service.model.dto;

import java.math.BigDecimal;

/**
 *
 * @author raymond (ray.a.ralph@gmail.com)
 */
public record RewardResponse(
        boolean isWinner,
        BigDecimal rewardAmount,
        String message
        ) {

}
