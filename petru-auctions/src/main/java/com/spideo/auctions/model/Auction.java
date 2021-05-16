package com.spideo.auctions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String name;

    String description;

    @Column(nullable = false)
    Instant startTime;

    @Column(nullable = false)
    Instant endTime;

    @Column(nullable = false)
    Integer startPrice;

    @ManyToOne
    @JoinColumn(nullable = false)
    AuctionHouse auctionHouse;

    @ManyToOne
    @JoinColumn(nullable = false)
    User seller;

    @OneToMany(mappedBy = "auction", fetch = FetchType.EAGER)
    @RestResource(path = "biddings")
    List<Bidding> biddings;

    public int getMaxBid() {
        if (this.biddings != null) {
            int max = 0;
            for (int i = 0; i < this.biddings.size(); i++) {
                final int price = biddings.get(i).getPrice();
                if (price > max)
                    max = price;
            }
            return max;
        } else
            return 0;
    }
    
}
