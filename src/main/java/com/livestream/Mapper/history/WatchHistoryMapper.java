package com.livestream.Mapper.history;

import com.livestream.DTO.request.history.WatchHistoryRequest;
import com.livestream.DTO.response.history.WatchHistoryResponse;
import com.livestream.Entity.history.WatchHistory;
import com.livestream.Mapper.livestream.LivestreamMapper;
import com.livestream.Mapper.user.UserMapper;
import com.livestream.Mapper.video.VideoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, LivestreamMapper.class, VideoMapper.class })
public interface WatchHistoryMapper {
    WatchHistory toWatchHistory(WatchHistoryRequest request);

    WatchHistoryResponse toWatchHistoryResponse(WatchHistory watchHistory);
}
