package com.livestream.Mapper.livestream;

import com.livestream.DTO.request.livestream.LivestreamCreationRequest;
import com.livestream.DTO.request.livestream.LivestreamUpdateRequest;
import com.livestream.DTO.response.livestream.LivestreamResponse;
import com.livestream.Entity.livestream.Livestream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LivestreamMapper {
    @Mapping(target = "channel", ignore = true)
    @Mapping(target = "category", ignore = true)
    Livestream toLivestream(LivestreamCreationRequest request);

    LivestreamResponse toLivestreamResponse(Livestream livestream);

    @Mapping(target = "channel", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateLivestream(@MappingTarget Livestream livestream, LivestreamUpdateRequest request);
}
