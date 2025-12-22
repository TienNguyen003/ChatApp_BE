package com.livestream.Controller;

import java.util.List;

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

import com.livestream.DTO.request.event.EventCompleteRequest;
import com.livestream.DTO.request.event.EventCreateRequest;
import com.livestream.DTO.request.event.EventUpdateRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.event.EventParticipationResponse;
import com.livestream.DTO.response.event.EventResponse;
import com.livestream.Service.event.EventService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}event")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventController {
    EventService eventService;

    @GetMapping
    ApiResponse<List<EventResponse>> listEvents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<EventResponse> pageData = eventService.listEvents(pageable);
        return ApiResponse.<List<EventResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @PostMapping
    ApiResponse<EventResponse> create(@Valid @RequestBody EventCreateRequest request) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.createEvent(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<EventResponse> update(@PathVariable int id, @Valid @RequestBody EventUpdateRequest request) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.updateEvent(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> delete(@PathVariable int id) {
        eventService.deleteEvent(id);
        return ApiResponse.<Void>builder().message("Xóa sự kiện thành công").build();
    }

    @PostMapping("/{id}/join")
    ApiResponse<EventParticipationResponse> join(@PathVariable int id) {
        return ApiResponse.<EventParticipationResponse>builder()
                .result(eventService.joinEvent(id))
                .build();
    }

    @PostMapping("/{id}/complete")
    ApiResponse<EventParticipationResponse> complete(
            @PathVariable int id,
            @Valid @RequestBody EventCompleteRequest request) {
        return ApiResponse.<EventParticipationResponse>builder()
                .result(eventService.completeEvent(id, request))
                .build();
    }
}
