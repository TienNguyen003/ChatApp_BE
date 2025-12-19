package com.livestream.Mapper.reward;

import com.livestream.DTO.response.reward.MissionResponse;
import com.livestream.Entity.gift.Gift;
import com.livestream.Entity.reward.Mission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MissionMapper {
    @Mapping(target = "rewardGift", expression = "java(toGiftInfo(mission.getRewardGift()))")
    MissionResponse toMissionResponse(Mission mission);

    default MissionResponse.RewardGiftInfo toGiftInfo(Gift gift) {
        if (gift == null)
            return null;
        return MissionResponse.RewardGiftInfo.builder()
                .id(gift.getId())
                .name(gift.getName())
                .iconUrl(gift.getIconUrl())
                .build();
    }
}
