package com.livestream.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.livestream.DTO.request.notification.NotificationRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.notification.NotificationResponse;
import com.livestream.Service.notification.NotificationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    NotificationService notificationService;

    @PostMapping
    ApiResponse<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequest request) {
        return ApiResponse.<NotificationResponse>builder()
                .result(notificationService.createNotification(request))
                .build();
    }

    @GetMapping("/my-notifications")
    ApiResponse<Page<NotificationResponse>> getMyNotifications(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return ApiResponse.<Page<NotificationResponse>>builder()
                .result(notificationService.getMyNotifications(pageable))
                .build();
    }

    @PutMapping("/{id}/read")
    ApiResponse<Void> markAsRead(@PathVariable int id) {
        notificationService.markAsRead(id);
        return ApiResponse.<Void>builder()
                .message("Đánh dấu đã đọc thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteNotification(@PathVariable int id) {
        notificationService.deleteNotification(id);
        return ApiResponse.<Void>builder().build();
    }
}
