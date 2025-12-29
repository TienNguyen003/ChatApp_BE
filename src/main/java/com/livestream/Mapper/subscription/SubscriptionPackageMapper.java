package com.livestream.Mapper.subscription;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.livestream.DTO.response.subscription.SubscriptionPackageResponse;
import com.livestream.Entity.subscription.SubscriptionPackage;
import com.livestream.Mapper.channel.ChannelMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionPackageMapper {
    ChannelMapper channelMapper;

    public SubscriptionPackageResponse toResponse(SubscriptionPackage entity) {
        if (entity == null) {
            return null;
        }

        List<String> benefits = entity.getBenefits() != null && !entity.getBenefits().isEmpty()
                ? Arrays.asList(entity.getBenefits().split(","))
                : List.of();

        return SubscriptionPackageResponse.builder()
                .id(entity.getId())
                .channel(channelMapper.toChannelResponse(entity.getChannel()))
                .tierLevel(entity.getTierLevel())
                .tierName(entity.getTierName())
                .price(entity.getPrice())
                .benefits(benefits)
                .description(entity.getDescription())
                .isActive(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
