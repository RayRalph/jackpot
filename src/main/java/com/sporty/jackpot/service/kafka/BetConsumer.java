package com.sporty.jackpot.service.kafka;

import com.sporty.jackpot.service.model.dto.BetRequest;
import com.sporty.jackpot.service.service.JackpotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author raymond
 */
@Service
public class BetConsumer {

    private static final Logger log = LoggerFactory.getLogger(BetConsumer.class);

    @Autowired
    private final JackpotService jackpotService;

    public BetConsumer(JackpotService jackpotService) {
        log.info("Ready to handle Bet");
        this.jackpotService = jackpotService;
    }

    @KafkaListener(topics = "jackpot-bets", groupId = "jackpot-group")
    public void handleBet(BetRequest bet) {
        try {
            log.info("Consumer received bet: {}", bet.betId());
            jackpotService.processContribution(
                    bet.jackpotId(),
                    bet.betId(),
                    bet.userId(),
                    bet.amount()
            );
        } catch (Exception e) {
            log.error("Consumer failed to process bet", e);
            throw e;
        }
    }
}
