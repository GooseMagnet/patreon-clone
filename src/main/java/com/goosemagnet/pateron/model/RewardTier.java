package com.goosemagnet.pateron.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class RewardTier {

    public RewardTier(String title, BigDecimal price, CreatorPage creatorPage) {
        this(null, title, price, creatorPage, new HashSet<>());
    }

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private BigDecimal price;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private CreatorPage page;

    @ManyToMany(mappedBy = "tiers")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<CreatorPost> posts;
}
