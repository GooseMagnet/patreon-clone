package com.goosemagnet.pateron.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Post")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreatorPost {

    public CreatorPost(CreatorPage page, String content, PostType type, Set<RewardTier> rewardTiers) {
        this(null, page, content, type, rewardTiers);
    }

    public CreatorPost(CreatorPage page, String content, PostType type) {
        this(null, page, content, type, new HashSet<>());
    }

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private CreatorPage page;

    @Lob
    private String content;

    @Enumerated(value = EnumType.STRING)
    private PostType type;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "post_tiers",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "reward_tier_id")
    )
    private Set<RewardTier> tiers;

    public void addToTier(RewardTier rewardTier) {
        tiers.add(rewardTier);
        rewardTier.getPosts().add(this);
    }
}
