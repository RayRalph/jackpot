package com.sporty.jackpot.service.repository;

import com.sporty.jackpot.service.model.entity.JackpotReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author raymond
 */
@Repository
public interface JackpotRewardRepository extends JpaRepository<JackpotReward, Long> {
    
}
