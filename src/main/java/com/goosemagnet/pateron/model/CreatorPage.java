package com.goosemagnet.pateron.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@Entity(name = "Page")
@NoArgsConstructor
@AllArgsConstructor
public class CreatorPage {

    public CreatorPage(Patron patron, LocalDate creationDate) {
        this(null,
                patron,
                creationDate,
                new ArrayList<>(),
                new TreeSet<>(Comparator.comparing(RewardTier::getPrice).thenComparingLong(RewardTier::getId))
        );
    }

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patron_id")
    private Patron patron;

    private LocalDate creationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    private List<CreatorPost> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    @OrderBy("price")
    private SortedSet<RewardTier> rewardTiers;
}
