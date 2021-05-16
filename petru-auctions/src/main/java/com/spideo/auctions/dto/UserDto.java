package com.spideo.auctions.dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.spideo.auctions.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 */
@Data
@NoArgsConstructor
@ToString
public class UserDto {

    public UserDto(String str) {
        final String pattern = "(\\d+)";
        final Pattern r = Pattern.compile(pattern);
        final Matcher m = r.matcher(str);
        if (m.find()) {
            final String numberstr = m.group(0);
            final long userid = Long.parseLong(numberstr);
            this.id = userid;
            System.out.println("parsed " + str + " to " + userid);
        } else
            throw new IllegalArgumentException(
                String.format("cannot interpret [%s] as user", str));
        
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    Long id;
    String name;
}
