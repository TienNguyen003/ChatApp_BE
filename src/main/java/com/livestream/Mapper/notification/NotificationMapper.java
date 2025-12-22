package com.livestream.Mapper.notification;

import org.mapstruct.Mapper;

import com.livestream.DTO.request.notification.NotificationRequest;
import com.livestream.DTO.response.notification.NotificationResponse;
import com.livestream.Entity.notification.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(NotificationRequest request);

    NotificationResponse toNotificationResponse(Notification notification);
}
