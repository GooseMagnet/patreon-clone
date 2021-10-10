package com.goosemagnet.pateron.persistence;

import com.goosemagnet.pateron.model.CreatorPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<CreatorPage, Long> {
}
