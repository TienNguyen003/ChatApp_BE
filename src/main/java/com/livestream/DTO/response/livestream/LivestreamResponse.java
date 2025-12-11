package com.livestream.DTO.response.livestream;

import com.livestream.DTO.response.category.CategoryResponse;
import com.livestream.DTO.response.channel.ChannelResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LivestreamResponse {
    int id;
    ChannelResponse channel;
    CategoryResponse category;
    String title;
    int viewersCount;
    String status;
    LocalDateTime startedAt;
    LocalDateTime endedAt;
    String streamUrl;
    String thumbnailUrl;
}
