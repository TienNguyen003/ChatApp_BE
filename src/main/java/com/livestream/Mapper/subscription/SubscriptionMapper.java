package com.livestream.Mapper.subscription;

import com.livestream.DTO.request.subscription.SubscriptionRequest;
import com.livestream.DTO.response.subscription.SubscriptionResponse;
import com.livestream.Entity.subscription.Subscription;
import com.livestream.Mapper.channel.ChannelMapper;
import com.livestream.Mapper.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, ChannelMapper.class })
public interface SubscriptionMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "channel", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startedAt", ignore = true)
    Subscription toSubscription(SubscriptionRequest request);

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);
}
