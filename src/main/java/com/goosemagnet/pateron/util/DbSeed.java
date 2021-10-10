package com.goosemagnet.pateron.util;

import com.github.javafaker.Faker;
import com.goosemagnet.pateron.model.*;
import com.goosemagnet.pateron.persistence.*;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Configuration
public class DbSeed {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RewardTierRepository rewardTierRepository;

    @Autowired
    private PledgeRepository pledgeRepository;

    @Bean
    void seedDatabase() {
        PostType[] postTypes = PostType.values();
        Faker faker = new Faker();
        val random = ThreadLocalRandom.current();

        val savedPages = new ArrayList<CreatorPage>();
        val savedPatrons = new ArrayList<Patron>();

        IntStream.rangeClosed(1, 200).forEach(ignored -> {
            val name = faker.name().username();
            val emailTemplate = "%s@%s";
            val date = faker.date().past(10000, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            val email = String.format(emailTemplate, name, faker.internet().domainName());
            val patron = new Patron(name, email, date);


            if (random.nextBoolean()) {
                val savedPage = pageRepository.save(new CreatorPage(patron, date.plusDays(random.nextInt(30))));
                savedPages.add(savedPage);

                savedPatrons.add(savedPage.getPatron());

                List<RewardTier> tiers = new ArrayList<>();
                IntStream.rangeClosed(0, random.nextInt(3))
                        .forEach(ignored2 -> {
                            val rewardTier = new RewardTier(faker.leagueOfLegends().rank(), new BigDecimal(random.nextInt(0, 50) + 1), savedPage);
                            tiers.add(rewardTierRepository.save(rewardTier));
                        });

                if (random.nextBoolean()) {
                    IntStream.rangeClosed(1, ThreadLocalRandom.current().nextInt(5) + 1)
                            .forEach(noop -> {
                                val type = postTypes[random.nextInt(postTypes.length)];
                                val post = new CreatorPost(savedPage, faker.rickAndMorty().quote(), type);

                                IntStream.rangeClosed(0, random.nextInt(tiers.size())).forEach(ignored2 -> post.addToTier(tiers.get(random.nextInt(tiers.size()))));

                                if (random.nextBoolean()) post.getTiers().clear();

                                postRepository.save(post);
                            });
                }
            }
        });

        IntStream.rangeClosed(0, savedPatrons.size()).forEach(ignored -> {
            val patron = savedPatrons.get(random.nextInt(savedPatrons.size()));

            for (int i = 0; i < random.nextInt(10); ++i) {
                val page = savedPages.get(random.nextInt(savedPages.size()));

                if (random.nextBoolean()) {
                    pledgeRepository.save(new Pledge(patron, page, new BigDecimal(random.nextInt(0, 200) + 1)));
                }
            }
        });

//        Patron goose = new Patron("Goose", "goose@email.com", LocalDate.of(2013, 4, 7));
//        Patron bulls = new Patron("Bulls", "bulls@nomail.com", LocalDate.of(2015, 9, 22));
//        Patron vreff = new Patron("Vreff", "vreff@vemail.com", LocalDate.of(2017, 11, 18));
//
//        CreatorPage goosePage = new CreatorPage(goose, LocalDate.of(2021, 1, 1));
//        CreatorPage vreffPage = new CreatorPage(vreff, LocalDate.of(2019, 1, 1));
//
//        patronRepository.save(bulls);
//        pageRepository.save(goosePage);
//        pageRepository.save(vreffPage);
    }
}
