package com.spideo.auctions.dto;

import com.spideo.auctions.model.Auction;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AuctionDto {

    Long id;

    String name;

    String description;

    Instant startTime;

    Instant endTime;

    Integer startPrice; // in cents

    UserDto seller;

    List<BiddingDto> biddings;

    public AuctionDto(Auction auction) {
        this.id = auction.getId();
        this.name = auction.getName();
        this.description = auction.getDescription();
        this.startPrice = auction.getStartPrice();
        this.startTime = auction.getStartTime();
        this.endTime = auction.getEndTime();
        this.seller = new UserDto(auction.getSeller());
        this.biddings = auction.getBiddings().stream()
                .map(it -> new BiddingDto(it))
                .collect(Collectors.toList());
    }
}
