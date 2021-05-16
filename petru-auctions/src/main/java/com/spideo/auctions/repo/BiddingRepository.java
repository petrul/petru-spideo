package com.spideo.auctions.repo;

import com.spideo.auctions.model.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface BiddingRepository extends JpaRepository<Bidding, Long> {
    List<Bidding> findByAuctionId(long auctionId);
}
