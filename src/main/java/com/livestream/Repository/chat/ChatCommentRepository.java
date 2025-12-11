package com.livestream.Repository.chat;

import com.livestream.Entity.chat.ChatComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatCommentRepository extends JpaRepository<ChatComment, Integer> {
    Page<ChatComment> findByLivestreamId(int livestreamId, Pageable pageable);
}
