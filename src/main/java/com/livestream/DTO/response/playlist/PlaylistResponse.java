package com.livestream.DTO.response.playlist;

import com.livestream.Entity.playlist.PlaylistVisibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistResponse {
    int id;
    int channelId;
    String channelName;
    String name;
    String description;
    PlaylistVisibility visibility;
    int videoCount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
