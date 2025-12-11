package com.livestream.Service.notification;

import com.livestream.DTO.request.notification.NotificationRequest;
import com.livestream.DTO.response.notification.NotificationResponse;
import com.livestream.Entity.notification.Notification;
import com.livestream.Entity.user.Users;
import com.livestream.Exception.AppException;
import com.livestream.Exception.ErrorCode;
import com.livestream.Mapper.notification.NotificationMapper;
import com.livestream.Repository.notification.NotificationRepository;
import com.livestream.Repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService {
    NotificationRepository notificationRepository;
    UserRepository userRepository;
    NotificationMapper notificationMapper;

    public NotificationResponse createNotification(NotificationRequest request) {
        Users user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Notification notification = notificationMapper.toNotification(request);
        notification.setUser(user);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        return notificationMapper.toNotificationResponse(notificationRepository.save(notification));
    }

    public Page<NotificationResponse> getMyNotifications(Pageable pageable) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Users user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return notificationRepository.findByUserId(user.getId(), pageable)
                .map(notificationMapper::toNotificationResponse);
    }

    public void markAsRead(int id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void deleteNotification(int id) {
        if (!notificationRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOTIFICATION_NOT_EXISTED);
        }
        notificationRepository.deleteById(id);
    }
}
