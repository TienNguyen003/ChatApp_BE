package com.livestream.Mapper.badge;

import com.livestream.DTO.response.badge.UserBadgeResponse;
import com.livestream.Entity.badge.UserBadge;
import com.livestream.Mapper.user.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class, BadgeMapper.class })
public interface UserBadgeMapper {
    UserBadgeResponse toUserBadgeResponse(UserBadge userBadge);
}
