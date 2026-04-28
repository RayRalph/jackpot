package com.sporty.jackpot.service.repository;

import com.sporty.jackpot.service.model.entity.Jackpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author raymond (ray.a.ralph@gmail.com)
 */
@Repository
public interface JackpotRepository extends JpaRepository<Jackpot, String> {

}
