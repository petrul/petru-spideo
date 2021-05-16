package com.spideo.auctions.dto;

import java.time.Instant;
import java.util.Date;

import com.spideo.auctions.model.Bidding;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.rest.core.annotation.RestResource;

/**
 *
 */
@Data
@NoArgsConstructor
@ToString
public class BiddingDto {
    public BiddingDto(Bidding bidding) {
        this.id = bidding.getId();
        this.auctionId = bidding.getAuction().getId();
        this.user = new UserDto(bidding.getUser());
        this.price = bidding.getPrice();
        this.date = bidding.getDate();
    }

    Long id;
    Long auctionId;

    @RestResource(path = "user", rel="user")
    UserDto user;

    Instant date;

    int price;
}
