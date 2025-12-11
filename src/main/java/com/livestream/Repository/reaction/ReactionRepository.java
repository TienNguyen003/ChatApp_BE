package com.livestream.Repository.reaction;

import com.livestream.Entity.reaction.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
    Page<Reaction> findByLivestreamId(int livestreamId, Pageable pageable);

    Optional<Reaction> findByUserIdAndLivestreamId(int userId, int livestreamId);
}
