package com.livestream.Mapper.clip;

import com.livestream.DTO.request.clip.ClipCreationRequest;
import com.livestream.DTO.response.clip.ClipResponse;
import com.livestream.Entity.clip.Clip;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClipMapper {
    Clip toClip(ClipCreationRequest request);

    ClipResponse toClipResponse(Clip clip);
}
