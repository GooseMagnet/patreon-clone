package com.goosemagnet.pateron.persistence;

import com.goosemagnet.pateron.model.Patron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {

    Optional<Patron> findByEmailIgnoreCase(@NonNull String email);

    Optional<Patron> findByUsernameIgnoreCase(@NonNull String username);

    @Query(
            value = "" +
                    "SELECT ptr.id,ptr.email,ptr.registration_date,ptr.username " +
                    "FROM patron ptr, page pg " +
                    "WHERE ptr.id = pg.patron_id",
            countQuery = "" +
                    "SELECT count(*) " +
                    "FROM patron ptr, page pg " +
                    "WHERE ptr.id = pg.patron_id",
            nativeQuery = true
    )
    Page<Patron> findCreators(Pageable pageable);
}
