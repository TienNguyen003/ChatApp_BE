package com.livestream.Mapper.notification;

import com.livestream.DTO.request.notification.NotificationRequest;
import com.livestream.DTO.response.notification.NotificationResponse;
import com.livestream.Entity.notification.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(NotificationRequest request);

    NotificationResponse toNotificationResponse(Notification notification);
}
