package com.livestream.Repository.gift;

import com.livestream.Entity.gift.UserGift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGiftRepository extends JpaRepository<UserGift, Integer> {
    Page<UserGift> findByUserId(int userId, Pageable pageable);

    Page<UserGift> findByChannelId(int channelId, Pageable pageable);
}
