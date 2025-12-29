package com.livestream.Repository.subscription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livestream.Entity.subscription.SubscriptionPackage;

@Repository
public interface SubscriptionPackageRepository extends JpaRepository<SubscriptionPackage, Integer> {
    List<SubscriptionPackage> findByChannelId(int channelId);

    List<SubscriptionPackage> findByChannelIdAndIsActiveTrue(int channelId);
}
