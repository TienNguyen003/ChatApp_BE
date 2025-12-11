package com.livestream.DTO.response.channel;

import com.livestream.DTO.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelResponse {
    int id;
    UserResponse user;
    String name;
    String description;
    String avatarUrl;
    String bannerUrl;
    int followersCount;
}
