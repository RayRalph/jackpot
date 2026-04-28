package com.sporty.jackpot.service.controller;

import com.sporty.jackpot.service.model.dto.BetRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author raymond
 */
@RestController
@RequestMapping("/api/bets")
public class BetController {
    
    private static final Logger log = LoggerFactory.getLogger(BetController.class);

    
    private final KafkaTemplate<String, BetRequest> kafkaTemplate;

    public BetController(KafkaTemplate<String, BetRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> placeBet(@Valid @RequestBody BetRequest bet) {
        var future = kafkaTemplate.send("jackpot-bets", bet.betId(), bet);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message sent successfully to partition {} with offset {}",
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message to Kafka: {}", ex.getMessage());
            }
        });

        return ResponseEntity.accepted().body("Bet " + bet.betId() + " submitted");
    }
}
