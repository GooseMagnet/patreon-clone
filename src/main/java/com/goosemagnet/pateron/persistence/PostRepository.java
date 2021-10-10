package com.goosemagnet.pateron.persistence;

import com.goosemagnet.pateron.model.CreatorPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<CreatorPost, Long> {

    @Query(
            value = "SELECT " +
                    "   id," +
                    "   type," +
                    "   content, " +
                    "   page_id " +
                    "FROM " +
                    "   post " +
                    "WHERE id IN ( " +
                    "   SELECT " +
                    "       post_tiers.post_id " +
                    "   FROM " +
                    "       reward_tier,pledge,post_tiers " +
                    "   WHERE " +
                    "       pledge.patron_id = ?1 AND " +
                    "       pledge.page_id = ?2 AND " +
                    "       pledge.pledge_amount >= reward_tier.price AND " +
                    "       pledge.page_id = reward_tier.page_id AND " +
                    "       post_tiers.reward_tier_id = reward_tier.id " +
                    "   UNION " +
                    "       (" +
                    "           SELECT id " +
                    "           FROM post " +
                    "           WHERE post.page_id = ?2 AND post.id NOT IN (SELECT post_id FROM post_tiers)" +
                    "       )" +
                    ")",
            nativeQuery = true
    )
    Slice<CreatorPost> getEligiblePosts(Long patronId, Long pageId, Pageable pageable);

    Page<CreatorPost> findAllByPageId(Long pageId, Pageable pageable);

    /**
     * REWARD_TIERS = PRICE
     * 457 = 14.00
     * 458 = 5.00
     *
     * // POSTS -> TIERS
     * 459 -> 458
     * 462 -> 458
     * 461 -> 458
     * 460 -> 457
     */
}
