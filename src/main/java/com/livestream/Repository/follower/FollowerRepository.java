package com.livestream.Repository.follower;

import com.livestream.Entity.follower.Follower;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {
    Page<Follower> findByFollowerId(int followerId, Pageable pageable);

    Page<Follower> findByChannelId(int channelId, Pageable pageable);

    Optional<Follower> findByFollowerIdAndChannelId(int followerId, int channelId);

    boolean existsByFollowerIdAndChannelId(int followerId, int channelId);
}
