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
import jakarta.validation.Valid;

import com.livestream.DTO.request.gift.GiftRequest;
import com.livestream.DTO.request.gift.SendGiftRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.gift.GiftResponse;
import com.livestream.Service.gift.GiftService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}gifts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftController {
    GiftService giftService;

    @PostMapping
    ApiResponse<GiftResponse> createGift(@Valid @RequestBody GiftRequest request) {
        return ApiResponse.<GiftResponse>builder()
                .result(giftService.createGift(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<GiftResponse> updateGift(
            @PathVariable int id,
            @Valid @RequestBody GiftRequest request) {
        return ApiResponse.<GiftResponse>builder()
                .result(giftService.updateGift(id, request))
                .build();
    }

    @GetMapping("/getAll")
    ApiResponse<List<GiftResponse>> getAllGiftsNoPagination() {
        return ApiResponse.<List<GiftResponse>>builder()
                .result(giftService.getAllGifts())
                .build();
    }

    @GetMapping("/list")
    ApiResponse<List<GiftResponse>> getAllGifts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GiftResponse> pageData = giftService.getAllGifts(pageable);
        return ApiResponse.<List<GiftResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @PostMapping("/send")
    ApiResponse<Void> sendGift(@RequestBody SendGiftRequest request) {
        giftService.sendGift(request);
        return ApiResponse.<Void>builder()
                .message("Gửi quà thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteGift(@PathVariable int id) {
        giftService.deleteGift(id);
        return ApiResponse.<Void>builder().build();
    }
}
