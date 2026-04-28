package com.sporty.jackpot.service.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 *
 * @author raymond (ray.a.ralph@gmail.com)
 */

public record BetRequest(
        @NotBlank(message = "Jackpot ID cannot be empty")
        String jackpotId,
        @NotBlank(message = "Bet ID cannot be empty")
        String betId,
        @NotBlank(message = "User ID cannot be empty")
        String userId,
        @NotNull(message = "Stake cannot be null")
        @DecimalMin(value = "0.01", message = "Stake must be at least 0.01")
        BigDecimal amount
        ) {

}
