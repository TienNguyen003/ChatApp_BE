package com.livestream.Repository.subscription;

import com.livestream.Entity.subscription.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Page<Subscription> findByUserId(int userId, Pageable pageable);

    Page<Subscription> findByChannelId(int channelId, Pageable pageable);

    Optional<Subscription> findByUserIdAndChannelId(int userId, int channelId);
}
