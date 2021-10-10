package com.goosemagnet.pateron.persistence;

import com.goosemagnet.pateron.model.Pledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PledgeRepository extends JpaRepository<Pledge, Long> {
}
