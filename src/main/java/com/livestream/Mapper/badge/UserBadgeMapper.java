package com.livestream.Mapper.badge;

import com.livestream.DTO.response.badge.UserBadgeResponse;
import com.livestream.Entity.badge.UserBadge;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserBadgeMapper {
    UserBadgeResponse toUserBadgeResponse(UserBadge userBadge);
}
