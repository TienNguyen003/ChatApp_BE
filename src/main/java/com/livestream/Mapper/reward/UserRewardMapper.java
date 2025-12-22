package com.livestream.Mapper.reward;

import com.livestream.DTO.response.reward.UserRewardResponse;
import com.livestream.Entity.reward.UserReward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRewardMapper {
    UserRewardResponse toUserRewardResponse(UserReward userReward);
}
