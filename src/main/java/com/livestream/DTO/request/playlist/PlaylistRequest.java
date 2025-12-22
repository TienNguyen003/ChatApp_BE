package com.livestream.DTO.request.playlist;

import com.livestream.Entity.playlist.PlaylistVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistRequest {
    @NotBlank
    @Size(max = 255)
    String name;

    @Size(max = 2000)
    String description;

    @NotNull
    PlaylistVisibility visibility;
}
