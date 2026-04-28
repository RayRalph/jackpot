package com.sporty.jackpot.service.kafka;

import com.sporty.jackpot.service.repository.JackpotContributionRepository;
import com.sporty.jackpot.service.repository.JackpotRepository;
import jakarta.transaction.Transactional;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

/**
 *
 * @author raymond
 */
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"jackpot-bets"})
@DirtiesContext 
public class BetConsumerE2ETest {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private JackpotRepository jackpotRepository;

    @Autowired
    private JackpotContributionRepository contributionRepository;

    @Test
    @Transactional
    @DisplayName("E2E: Should process Kafka event and update DB")
    void shouldProcessKafkaEvent() {
        String jackpotId = "J1";
        String jsonPayload = """
            {
                "jackpotId": "%s",
                "betId": "B-999",
                "userId": "user-1",
                "amount": 50.00
            }
            """.formatted(jackpotId);

        kafkaTemplate.send("jackpot-bets", jsonPayload);

        await().atMost(5, SECONDS).untilAsserted(() -> {
            var contribution = contributionRepository.findByBetId("B-999");
            assertThat(contribution).isPresent();

            var jackpot = jackpotRepository.findById(jackpotId).orElseThrow();
            assertThat(jackpot.getCurrentPool()).isEqualByComparingTo("1050.00");
        });
    }
    
}
