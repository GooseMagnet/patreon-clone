package com.goosemagnet.pateron.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Patron {

    public Patron(String username, String email, LocalDate registrationDate) {
        this(null, username, email, registrationDate, null);
    }

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;

    private LocalDate registrationDate;

    @JsonIgnore
    @OneToOne(mappedBy = "patron", cascade = CascadeType.ALL)
    private CreatorPage page;
}
