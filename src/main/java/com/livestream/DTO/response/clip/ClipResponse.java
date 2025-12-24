package com.livestream.DTO.response.clip;

import com.livestream.DTO.response.channel.ChannelResponse;
import com.livestream.DTO.response.livestream.LivestreamResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClipResponse {
    int id;
    LivestreamResponse livestream;
    ChannelResponse channel;
    String title;
    String startTime;
    String endTime;
    String videoUrl;
    String thumbnailUrl;
    int view;
}
