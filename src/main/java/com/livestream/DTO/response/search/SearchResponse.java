package com.livestream.DTO.response.search;

import com.livestream.DTO.response.channel.ChannelResponse;
import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.DTO.response.video.VideoResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchResponse {
    List<ChannelResponse> channels;
    List<LivestreamResponse> livestreams;
    List<VideoResponse> videos;
    int totalResults;
}
