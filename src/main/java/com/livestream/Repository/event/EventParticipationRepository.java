package com.livestream.Repository.event;

import com.livestream.Entity.event.EventParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventParticipationRepository extends JpaRepository<EventParticipation, Integer> {
    Optional<EventParticipation> findByEventIdAndUserId(int eventId, int userId);
}
