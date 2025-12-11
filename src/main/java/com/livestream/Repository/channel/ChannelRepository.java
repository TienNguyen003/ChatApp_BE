package com.livestream.Repository.channel;

import com.livestream.Entity.channel.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    Optional<Channel> findByUserId(int userId);
}
