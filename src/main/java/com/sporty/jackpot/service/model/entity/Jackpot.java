package com.sporty.jackpot.service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author raymond (ray.a.ralph@gmail.com)
 */
@Entity
@Table(name = "JACKPOT")
public class Jackpot {
    @Id
    @Column(nullable = false, updatable = false)
    private String jackpotId;
    @Column(precision = 19, scale = 4)
    private BigDecimal currentPool;
    @Column(precision = 19, scale = 4)
    private BigDecimal initialPool;
    private String contributionType; // "FIXED", "VARIABLE"
    private String rewardType;       // "FIXED", "VARIABLE"
    @Column(precision = 5, scale = 4)
    private BigDecimal rewardRate; // Base chance

    @Column(precision = 19, scale = 4)
    private BigDecimal rewardThreshold; // Pool amount where chance becomes 100%
    @Column(precision = 5, scale = 4)
    private BigDecimal contributionRate; // e.g., 0.10 for 10%

    @Column(precision = 5, scale = 4)
    private BigDecimal decayRate; // For variable contribution

    @Version
    private Long version = 0L; 

    public String getJackpotId() {
        return jackpotId;
    }

    public void setJackpotId(String jackpotId) {
        this.jackpotId = jackpotId;
    }

    public BigDecimal getCurrentPool() {
        return currentPool;
    }

    public void setCurrentPool(BigDecimal currentPool) {
        this.currentPool = currentPool;
    }

    public BigDecimal getInitialPool() {
        return initialPool;
    }

    public void setInitialPool(BigDecimal initialPool) {
        this.initialPool = initialPool;
    }

    public String getContributionType() {
        return contributionType;
    }

    public void setContributionType(String contributionType) {
        this.contributionType = contributionType;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public BigDecimal getRewardRate() {
        return rewardRate;
    }

    public void setRewardRate(BigDecimal rewardRate) {
        this.rewardRate = rewardRate;
    }

    public BigDecimal getRewardThreshold() {
        return rewardThreshold;
    }

    public void setRewardThreshold(BigDecimal rewardThreshold) {
        this.rewardThreshold = rewardThreshold;
    }
    
    

    public BigDecimal getContributionRate() {
        return contributionRate;
    }

    public void setContributionRate(BigDecimal contributionRate) {
        this.contributionRate = contributionRate;
    }

    public BigDecimal getDecayRate() {
        return decayRate;
    }

    public void setDecayRate(BigDecimal decayRate) {
        this.decayRate = decayRate;
    }
    
    

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.jackpotId);
        hash = 47 * hash + Objects.hashCode(this.currentPool);
        hash = 47 * hash + Objects.hashCode(this.initialPool);
        hash = 47 * hash + Objects.hashCode(this.contributionType);
        hash = 47 * hash + Objects.hashCode(this.rewardType);
        hash = 47 * hash + Objects.hashCode(this.rewardRate);
        hash = 47 * hash + Objects.hashCode(this.rewardThreshold);
        hash = 47 * hash + Objects.hashCode(this.contributionRate);
        hash = 47 * hash + Objects.hashCode(this.decayRate);
        hash = 47 * hash + Objects.hashCode(this.version);
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
        final Jackpot other = (Jackpot) obj;
        if (!Objects.equals(this.jackpotId, other.jackpotId)) {
            return false;
        }
        if (!Objects.equals(this.contributionType, other.contributionType)) {
            return false;
        }
        if (!Objects.equals(this.rewardType, other.rewardType)) {
            return false;
        }
        if (!Objects.equals(this.currentPool, other.currentPool)) {
            return false;
        }
        if (!Objects.equals(this.initialPool, other.initialPool)) {
            return false;
        }
        if (!Objects.equals(this.rewardRate, other.rewardRate)) {
            return false;
        }
        if (!Objects.equals(this.rewardThreshold, other.rewardThreshold)) {
            return false;
        }
        if (!Objects.equals(this.contributionRate, other.contributionRate)) {
            return false;
        }
        if (!Objects.equals(this.decayRate, other.decayRate)) {
            return false;
        }
        return Objects.equals(this.version, other.version);
    }
    
    
}
