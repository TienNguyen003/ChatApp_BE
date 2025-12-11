package com.livestream.Repository.badge;

import com.livestream.Entity.badge.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    Optional<Badge> findByName(String name);

    boolean existsByName(String name);
}
