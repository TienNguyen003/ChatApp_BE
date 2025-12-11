package com.livestream.Repository.badge;

import com.livestream.Entity.badge.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Integer> {
    List<UserBadge> findByUserId(int userId);
}
