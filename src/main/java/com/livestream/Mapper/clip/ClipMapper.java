package com.livestream.Mapper.clip;

import com.livestream.DTO.request.clip.ClipCreationRequest;
import com.livestream.DTO.response.clip.ClipResponse;
import com.livestream.Entity.clip.Clip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClipMapper {
    @Mapping(target = "livestream", ignore = true)
    @Mapping(target = "channel", ignore = true)
    Clip toClip(ClipCreationRequest request);

    ClipResponse toClipResponse(Clip clip);
}
