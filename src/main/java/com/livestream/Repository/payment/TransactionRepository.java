package com.livestream.Repository.payment;

import com.livestream.Entity.payment.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Page<Transaction> findByUserId(int userId, Pageable pageable);

    Page<Transaction> findByStatus(String status, Pageable pageable);

    Page<Transaction> findByUserIdOrderByCreatedAtDesc(int userId, Pageable pageable);
}
