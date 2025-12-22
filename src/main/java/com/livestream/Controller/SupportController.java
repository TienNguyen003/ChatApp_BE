package com.livestream.Controller;

import com.livestream.DTO.request.support.SupportTicketRequest;
import com.livestream.DTO.request.support.SupportTicketUpdateRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.support.SupportTicketResponse;
import com.livestream.Service.support.SupportService;
import com.livestream.Util.PaginationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}help")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SupportController {
    SupportService supportService;

    @PostMapping("/tickets")
    ApiResponse<SupportTicketResponse> createTicket(@Valid @RequestBody SupportTicketRequest request) {
        return ApiResponse.<SupportTicketResponse>builder()
                .result(supportService.createTicket(request))
                .build();
    }

    @GetMapping("/my-tickets")
    ApiResponse<List<SupportTicketResponse>> myTickets(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<SupportTicketResponse> pageData = supportService.myTickets(pageable);
        return ApiResponse.<List<SupportTicketResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    // Admin endpoints
    @PutMapping("/tickets/{id}")
    ApiResponse<SupportTicketResponse> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody SupportTicketUpdateRequest request) {
        return ApiResponse.<SupportTicketResponse>builder()
                .result(supportService.updateTicket(id, request))
                .build();
    }

    @DeleteMapping("/tickets/{id}")
    ApiResponse<Void> deleteTicket(@PathVariable Long id) {
        supportService.deleteTicket(id);
        return ApiResponse.<Void>builder().build();
    }
}
