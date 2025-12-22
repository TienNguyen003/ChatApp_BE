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

import com.livestream.DTO.request.tag.TagRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.tag.TagResponse;
import com.livestream.Service.tag.TagService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}tags")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {
    TagService tagService;

    @PostMapping
    ApiResponse<TagResponse> createTag(@Valid @RequestBody TagRequest request) {
        return ApiResponse.<TagResponse>builder()
                .result(tagService.createTag(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<TagResponse> updateTag(
            @PathVariable int id,
            @Valid @RequestBody TagRequest request) {
        return ApiResponse.<TagResponse>builder()
                .result(tagService.updateTag(id, request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<TagResponse> getTag(@PathVariable int id) {
        return ApiResponse.<TagResponse>builder()
                .result(tagService.getTag(id))
                .build();
    }

    @GetMapping("/list")
    ApiResponse<List<TagResponse>> getAllTags(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<TagResponse> pageData = tagService.getAllTags(pageable);
        return ApiResponse.<List<TagResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @GetMapping("/trending")
    ApiResponse<List<TagResponse>> getTrendingTags(
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.<List<TagResponse>>builder()
                .result(tagService.getTrendingTags(limit))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
        return ApiResponse.<Void>builder().build();
    }
}
