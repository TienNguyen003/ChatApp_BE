package com.livestream.Repository.reward;

import com.livestream.Entity.reward.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRewardRepository extends JpaRepository<UserReward, Integer> {
}
