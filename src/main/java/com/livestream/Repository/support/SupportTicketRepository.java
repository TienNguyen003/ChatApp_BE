package com.livestream.Repository.support;

import com.livestream.Entity.support.SupportTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Integer> {
    Page<SupportTicket> findByUserId(int userId, Pageable pageable);
}
