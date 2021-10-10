package com.goosemagnet.pateron.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Pledge {

    public Pledge(Patron patron, CreatorPage creatorPage, BigDecimal pledgeAmount) {
        this(null, patron, creatorPage, pledgeAmount);
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Patron patron;

    @OneToOne
    private CreatorPage page;

    private BigDecimal pledgeAmount;
}
