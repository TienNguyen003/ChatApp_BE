package com.livestream.Mapper.subscription;

import org.mapstruct.Mapper;

import com.livestream.DTO.request.subscription.SubscriptionRequest;
import com.livestream.DTO.response.subscription.SubscriptionResponse;
import com.livestream.Entity.subscription.Subscription;
import com.livestream.Mapper.user.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class, SubscriptionPackageMapper.class })
public interface SubscriptionMapper {
    Subscription toSubscription(SubscriptionRequest request);

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);
}
