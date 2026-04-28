package com.sporty.jackpot.service.controller;

import com.sporty.jackpot.service.model.dto.BetRequest;
import com.sporty.jackpot.service.model.dto.RewardResponse;
import com.sporty.jackpot.service.service.JackpotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author raymond
 */
@RestController
@RequestMapping("/api/jackpot")
public class JackpotController {
    
    private final JackpotService jackpotService;

    public JackpotController(JackpotService jackpotService) {
        this.jackpotService = jackpotService;
    }

    @PostMapping("/{jackpotId}/evaluate")
    public ResponseEntity<RewardResponse> evaluateReward(
            @PathVariable String jackpotId,
            @RequestBody BetRequest bet) {

        // Delegating to service for calculation/db update
        RewardResponse result = jackpotService.evaluateAndReward(jackpotId, bet);

        return ResponseEntity.ok(result);
    }
}
