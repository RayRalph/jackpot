package com.sporty.jackpot.service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author raymond (ray.a.ralph@gmail.com)
 */
@Entity
@Table(name = "JACKPOT_CONTRIBUTION", indexes = {
    @Index(name = "idx_contrib_jackpot", columnList = "jackpotId"),
    @Index(name = "idx_contrib_bet", columnList = "betId")
})
public class JackpotContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String betId;

    @Column(nullable = false, updatable = false)
    private String userId;

    @Column(nullable = false, updatable = false)
    private String jackpotId;

    @Column(nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal stakeAmount;

    @Column(nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal contributionAmount;

    @Column(nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal currentJackpotAmount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBetId() {
        return betId;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJackpotId() {
        return jackpotId;
    }

    public void setJackpotId(String jackpotId) {
        this.jackpotId = jackpotId;
    }

    public BigDecimal getStakeAmount() {
        return stakeAmount;
    }

    public void setStakeAmount(BigDecimal stakeAmount) {
        this.stakeAmount = stakeAmount;
    }

    public BigDecimal getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(BigDecimal contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public BigDecimal getCurrentJackpotAmount() {
        return currentJackpotAmount;
    }

    public void setCurrentJackpotAmount(BigDecimal currentJackpotAmount) {
        this.currentJackpotAmount = currentJackpotAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.betId);
        hash = 23 * hash + Objects.hashCode(this.userId);
        hash = 23 * hash + Objects.hashCode(this.jackpotId);
        hash = 23 * hash + Objects.hashCode(this.stakeAmount);
        hash = 23 * hash + Objects.hashCode(this.contributionAmount);
        hash = 23 * hash + Objects.hashCode(this.currentJackpotAmount);
        hash = 23 * hash + Objects.hashCode(this.createdAt);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JackpotContribution other = (JackpotContribution) obj;
        if (!Objects.equals(this.betId, other.betId)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.jackpotId, other.jackpotId)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.stakeAmount, other.stakeAmount)) {
            return false;
        }
        if (!Objects.equals(this.contributionAmount, other.contributionAmount)) {
            return false;
        }
        if (!Objects.equals(this.currentJackpotAmount, other.currentJackpotAmount)) {
            return false;
        }
        return Objects.equals(this.createdAt, other.createdAt);
    }

    @Override
    public String toString() {
        return "JackpotContribution{" + "id=" + id + ", betId=" + betId + ", userId=" + userId + ", jackpotId=" + jackpotId + ", stakeAmount=" + stakeAmount + ", contributionAmount=" + contributionAmount + ", currentJackpotAmount=" + currentJackpotAmount + ", createdAt=" + createdAt + '}';
    }

 
}
