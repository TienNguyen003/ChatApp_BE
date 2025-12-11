package com.livestream.Repository.notification;

import com.livestream.Entity.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Page<Notification> findByUserId(int userId, Pageable pageable);

    Page<Notification> findByUserIdAndIsRead(int userId, boolean isRead, Pageable pageable);
}
