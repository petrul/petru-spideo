package com.spideo.auctions.repo;

import java.util.List;

import com.spideo.auctions.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByAuctionHouseId(long auctionHouseId);
}
