package com.livestream.Mapper.video;

import com.livestream.DTO.request.video.VideoCreationRequest;
import com.livestream.DTO.request.video.VideoUpdateRequest;
import com.livestream.DTO.response.video.VideoResponse;
import com.livestream.Entity.video.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    Video toVideo(VideoCreationRequest request);

    VideoResponse toVideoResponse(Video video);

    void updateVideo(@MappingTarget Video video, VideoUpdateRequest request);
}
