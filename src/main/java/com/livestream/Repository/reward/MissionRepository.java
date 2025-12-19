package com.livestream.Repository.reward;

import com.livestream.Entity.reward.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {
    Optional<Mission> findByCode(String code);

    boolean existsByCode(String code);
}
