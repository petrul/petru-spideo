package com.spideo.auctions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.Instant;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bidding {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @ManyToOne
    Auction auction;

    @ManyToOne
    @RestResource(path = "user", rel="user")
    User user;

    Instant date;

    int price;

}
