package com.spideo.auctions.model;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class AuctionTest {

    @Test
    void maxBid() {
        final Auction auction = new Auction();
        assertNull(auction.getBiddings());
        assertEquals(0, auction.getMaxBid());
        auction.setBiddings(Lists.emptyList());
        assertEquals(0, auction.getMaxBid());
        List<Bidding> biddings = List.of(
                1, 2, 3
        ).stream().map(it -> new Bidding((long) it, null, null, it))
                .collect(Collectors.toList());
        auction.setBiddings(biddings);
        assertEquals(3, auction.getMaxBid());
    }
}