package com.spideo.auctions.repo;

import com.spideo.auctions.model.AuctionHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface AuctionHouseRepository extends JpaRepository<AuctionHouse, Long> {
}
