package com.livestream.Mapper.badge;

import com.livestream.DTO.request.badge.BadgeRequest;
import com.livestream.DTO.response.badge.BadgeResponse;
import com.livestream.Entity.badge.Badge;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BadgeMapper {
    Badge toBadge(BadgeRequest request);

    BadgeResponse toBadgeResponse(Badge badge);

    void updateBadge(@MappingTarget Badge badge, BadgeRequest request);
}
