package com.spideo.auctions.rest;

import com.spideo.auctions.dto.AuctionDto;
import com.spideo.auctions.dto.BiddingDto;
import com.spideo.auctions.model.Auction;
import com.spideo.auctions.model.Bidding;
import com.spideo.auctions.model.User;
import com.spideo.auctions.repo.AuctionRepository;
import com.spideo.auctions.repo.BiddingRepository;
import com.spideo.auctions.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
@RequestMapping("${spring.data.rest.basePath}/facade")
@CrossOrigin
public class FacadeController {
    @Autowired
    BiddingRepository biddingRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "biddings")
    @ResponseBody
    List<BiddingDto> getBiddings(@RequestParam long auctionId) {
        return this.biddingRepository.findByAuctionId(auctionId)
                .stream()
                .map( it -> new BiddingDto(it))
                .collect(Collectors.toList());
    }

    @GetMapping(path = "auctions")
    @ResponseBody
    List<AuctionDto> getAuctions(@RequestParam long auctionHouseId) {
        return this.auctionRepository.findByAuctionHouseId(auctionHouseId)
                .stream()
                .map( it -> new AuctionDto(it))
                .collect(Collectors.toList());
    }

    @GetMapping(path="auction")
    @ResponseBody
    AuctionDto getAuction(@RequestParam long auctionId) {
        return new AuctionDto(this.auctionRepository.findById(auctionId).get());
    }

    @PostMapping(path="bid")
    @ResponseBody
    public BiddingDto bid(@RequestBody BiddingDto biddingDto) {
        final User user = this.userRepository.findById(biddingDto.getUser().getId()).get();
        final Auction auction = this.auctionRepository.findById(biddingDto.getAuctionId()).get();

        if (auction.getEndTime() !=  null && auction.getEndTime().isBefore(Instant.now()))
            throw new IllegalStateException(String.format("auction %s has ended at %s",
                    auction.getId(),
                    auction.getEndTime()));

        final int newPrice = biddingDto.getPrice();

        int maxBid = auction.getMaxBid();
        if (newPrice <= maxBid)
            throw new IllegalArgumentException("provided price must be higher than existant bids, max price = " + maxBid);

        Bidding bidding = new Bidding(null, auction, user, Instant.now(), newPrice);
        bidding = this.biddingRepository.save(bidding);

        return new BiddingDto(bidding);
    }
}
