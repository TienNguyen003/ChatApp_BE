package com.livestream.Mapper.tag;

import com.livestream.DTO.request.tag.TagRequest;
import com.livestream.DTO.response.tag.TagResponse;
import com.livestream.Entity.tag.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mapping(target = "livestreams", ignore = true)
    @Mapping(target = "videos", ignore = true)
    Tag toTag(TagRequest request);

    TagResponse toTagResponse(Tag tag);

    @Mapping(target = "livestreams", ignore = true)
    @Mapping(target = "videos", ignore = true)
    void updateTag(@MappingTarget Tag tag, TagRequest request);
}
