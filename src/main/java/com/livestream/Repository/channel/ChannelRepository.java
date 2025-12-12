package com.livestream.Repository.channel;

import com.livestream.Entity.channel.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    Optional<Channel> findByUserId(int userId);

    Optional<Channel> findByStreamKey(String streamKey);

    @Query("SELECT c FROM Channel c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Channel> searchChannels(@Param("keyword") String keyword, Pageable pageable);

    Page<Channel> findAllByOrderByFollowersCountDesc(Pageable pageable);
}
