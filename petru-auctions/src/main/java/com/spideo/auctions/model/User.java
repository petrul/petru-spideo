package com.spideo.auctions.model;

import com.spideo.auctions.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    public static User fromDto(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName());
    }
}
