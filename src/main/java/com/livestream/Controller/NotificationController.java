package com.livestream.Controller;

import com.livestream.DTO.request.notification.NotificationRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.notification.NotificationResponse;
import com.livestream.Service.notification.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    NotificationService notificationService;

    @PostMapping
    ApiResponse<NotificationResponse> createNotification(@RequestBody NotificationRequest request) {
        return ApiResponse.<NotificationResponse>builder()
                .result(notificationService.createNotification(request))
                .build();
    }

    @GetMapping("/my-notifications")
    ApiResponse<Page<NotificationResponse>> getMyNotifications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
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
