package com.spideo.auctions;

import com.spideo.auctions.dto.BiddingDto;
import com.spideo.auctions.model.Auction;
import com.spideo.auctions.model.AuctionHouse;
import com.spideo.auctions.model.Bidding;
import com.spideo.auctions.model.User;
import com.spideo.auctions.repo.AuctionHouseRepository;
import com.spideo.auctions.repo.AuctionRepository;
import com.spideo.auctions.repo.BiddingRepository;
import com.spideo.auctions.repo.UserRepository;
import com.spideo.auctions.rest.FacadeController;
import com.spideo.auctions.services.WordService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Configuration
public class PopulateDatabase {

    final int nrlines = 10;

    @Autowired
    WordService wordService;


    String randomAdjectiveAndNoun() {
        return this.wordService.randomAdjectiveAndNoun()
                .stream().map(StringUtils::capitalize)
                .collect(Collectors.joining(" "));

    }
    String randomAuctionHouseName() {
        return this.randomAdjectiveAndNoun() + " Auction House";
    }

    String randomAuctionName() {
        return this.randomAdjectiveAndNoun() + " Auction";
    }

    @Bean
    CommandLineRunner databasePopulator(UserRepository userRepository,
                                        AuctionHouseRepository auctionHouseRepository,
                                        AuctionRepository auctionRepository,
                                        BiddingRepository biddingRepository,
                                        FacadeController facadeController)
    {
        return (args) -> {

            if (userRepository.count() == 0) {
                for (long i = 1; i <= nrlines; i++) {
                    User user = User.builder()
                            .id(i)
                            .name(this.randomAdjectiveAndNoun())
                            .build();
                    userRepository.save(user);
                }
            }

            List<User> users = userRepository.findAll();

            if (auctionHouseRepository.count() == 0) {
                for (long i = 1; i <= nrlines; i++) {
                    AuctionHouse auctionHouse = AuctionHouse.builder()
                        .id(i)
                        .name(this.randomAuctionHouseName())
                        .build();
                    final AuctionHouse retrievedAh = auctionHouseRepository.save(auctionHouse);

                    final String[] indemnuri = {
                            "You should definitely buy this!",
                            "Take a look.",
                            "Great for everyday use",
                            "Poor man's tool still a great acquisition!",
                            "Great-a-verick!",
                            "Great to have!",
                            "Recommended by SuchAndSuch Inc.",
                            "N'importe quoi.",
                            "As seen on TV.",
                            "Parcă ne cunoaștem.",
                            "True oxymoron.",
                            "Flackle tackle.",
                            "Not buying this makes you a complete and a true moron."
                    };
                    for (long ai = 1; ai <= nrlines; ai++) {
                        final String name = this.randomAuctionName();
                        final User seller = users.get(new Random().nextInt(users.size()));
                        String[] split = name.split(" ");
                        final String desc = "This is more or less a " + name
                                + ". It is " + split[0].toLowerCase() + " and it sure is a " + split[1].toLowerCase()
                                + ". " + indemnuri[new Random().nextInt(indemnuri.length)];


                        int nrWeeks = 1 + new Random().nextInt(3);
                        Instant nextweek = Instant.now().plus(7 * nrWeeks, ChronoUnit.DAYS);
                        
                        Auction auction = Auction.builder()
                                .id(ai)
                                .name(name)
                                .description(desc)
                                .seller(seller)
                                .startTime(Instant.now())
                                .endTime(nextweek)
                                .startPrice(new Random().nextInt(20))
                                .auctionHouse(retrievedAh)
                                .build();
                        final Auction savedAuction = auctionRepository.save(auction);

                        int biddingPrice = savedAuction.getStartPrice();

                        for (long j = 1; j < 11; j++) {
                            final User u = users.get(new Random().nextInt(users.size()));
                            int delta = 1 + new Random().nextInt(4);
                            biddingPrice += delta;

                            Bidding bidding = Bidding
                                    .builder()
                                    .id(j)
                                    .user(u)
                                    .auction(savedAuction)
                                    .price(biddingPrice)
                                    .date(Instant.now())
                                    .build();

                            facadeController.bid(new BiddingDto(bidding));
                        }
                    }
                }
            }
        };
    }
}
