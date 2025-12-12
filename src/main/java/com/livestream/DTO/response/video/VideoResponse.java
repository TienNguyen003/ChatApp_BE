package com.livestream.DTO.response.video;

import java.time.LocalDateTime;

import com.livestream.DTO.response.category.CategoryResponse;
import com.livestream.DTO.response.channel.ChannelResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoResponse {
    int id;
    ChannelResponse channel;
    CategoryResponse category;
    String title;
    String description;
    int views;
    String duration;
    String videoUrl;
    String thumbnailUrl;

    LocalDateTime uploadedAt;
}
