package com.goosemagnet.pateron.controller;

import com.goosemagnet.pateron.model.Patron;
import com.goosemagnet.pateron.persistence.PatronRepository;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Value
@RestController
public class PatronController {

    PatronRepository patronRepository;

    @GetMapping("/creators")
    public Page<Patron> getAllCreators(Pageable pageable) {
        Page<Patron> creators = patronRepository.findCreators(pageable);
        log.info("Number of Creators: {}", creators.getTotalElements());
        return creators;
    }

    @GetMapping("/patrons")
    public Page<Patron> getAllPatrons(Pageable pageable) {
        Page<Patron> patrons = patronRepository.findAll(pageable);
        log.info("Number of Patrons: {}", patrons.getTotalElements());
        return patrons;
    }

    @GetMapping(path = "/patrons/{id}")
    public Patron getPatronById(@PathVariable("id") Long patronId) {
        return patronRepository.findById(patronId).orElseThrow();
    }

    @GetMapping(path = "/creators/{id}")
    public Patron getCreatorById(@PathVariable("id") Long patronId) {
        Page<Patron> creators = patronRepository.findCreators(Pageable.unpaged());
        return creators.stream()
                .filter(patron -> patron.getId().equals(patronId))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Patron with id: " + patronId + " is not a creator."));
    }
}
