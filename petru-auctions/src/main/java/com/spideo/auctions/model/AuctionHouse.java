package com.spideo.auctions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionHouse {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    String name;

    @OneToMany(mappedBy = "auctionHouse")
    @RestResource(path = "auctions")
    List<Auction> auctions;

}
