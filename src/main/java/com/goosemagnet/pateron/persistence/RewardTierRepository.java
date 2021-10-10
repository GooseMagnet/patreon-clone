package com.goosemagnet.pateron.persistence;

import com.goosemagnet.pateron.model.RewardTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardTierRepository extends JpaRepository<RewardTier, Long> {
}
