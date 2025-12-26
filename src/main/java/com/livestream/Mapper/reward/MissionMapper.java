package com.livestream.Mapper.reward;

import org.mapstruct.Mapper;

import com.livestream.DTO.response.reward.MissionResponse;
import com.livestream.Entity.gift.Gift;
import com.livestream.Entity.reward.Mission;

@Mapper(componentModel = "spring")
public interface MissionMapper {
    MissionResponse toMissionResponse(Mission mission);

    default MissionResponse.RewardGiftInfo toGiftInfo(Gift gift) {
        if (gift == null)
            return null;
        return MissionResponse.RewardGiftInfo.builder()
                .id(gift.getId())
                .name(gift.getName())
                .color(gift.getColor())
                .iconUrl(gift.getIconUrl())
                .build();
    }
}
