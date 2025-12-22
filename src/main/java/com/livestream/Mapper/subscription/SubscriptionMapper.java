package com.livestream.Mapper.subscription;

import com.livestream.DTO.request.subscription.SubscriptionRequest;
import com.livestream.DTO.response.subscription.SubscriptionResponse;
import com.livestream.Entity.subscription.Subscription;
import com.livestream.Mapper.channel.ChannelMapper;
import com.livestream.Mapper.user.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class, ChannelMapper.class })
public interface SubscriptionMapper {
    Subscription toSubscription(SubscriptionRequest request);

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);
}
