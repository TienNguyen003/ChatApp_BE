package com.livestream.Repository.ban;

import com.livestream.Entity.ban.ChannelBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ChannelBanRepository extends JpaRepository<ChannelBan, Integer> {
    @Query("SELECT b FROM ChannelBan b WHERE b.channel.id = :channelId AND b.user.id = :userId " +
            "AND (b.banType = 'PERMANENT' OR b.expiresAt > :now)")
    Optional<ChannelBan> findActiveBan(int channelId, int userId, LocalDateTime now);

    @Query("SELECT b FROM ChannelBan b WHERE b.channel.id = :channelId " +
            "AND (b.banType = 'PERMANENT' OR b.expiresAt > :now)")
    Page<ChannelBan> findActiveBans(int channelId, LocalDateTime now, Pageable pageable);

    Page<ChannelBan> findByChannelId(int channelId, Pageable pageable);
}
