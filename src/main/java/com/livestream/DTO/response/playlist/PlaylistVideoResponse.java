package com.livestream.DTO.response.playlist;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistVideoResponse {
    int id;
    int playlistId;
    int videoId;
    String videoTitle;
    String thumbnail;
    int duration;
    int views;
    int position;
    LocalDateTime addedAt;
}
