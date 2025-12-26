package com.livestream.Repository.reward;

import com.livestream.Entity.reward.UserMissionProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMissionProgressRepository extends JpaRepository<UserMissionProgress, Integer> {
    Optional<UserMissionProgress> findByUserIdAndMissionId(int userId, int missionId);

    Page<UserMissionProgress> findByUserId(int userId, Pageable pageable);
}
