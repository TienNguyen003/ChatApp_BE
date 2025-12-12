package com.livestream.DTO.request.playlist;

import com.livestream.Entity.playlist.PlaylistVisibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistRequest {
    String name;
    String description;
    PlaylistVisibility visibility;
}
