package com.sporty.jackpot.service.repository;

import com.sporty.jackpot.service.model.entity.JackpotContribution;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author raymond
 */
@Repository
public interface JackpotContributionRepository extends JpaRepository<JackpotContribution, String> {
    
    List<JackpotContribution> findByJackpotId(String jackpotId);
    Optional<JackpotContribution> findByBetId(String betId);
}
