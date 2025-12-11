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

import com.livestream.DTO.request.category.CategoryRequest;
import com.livestream.DTO.response.ApiResponse;
import com.livestream.DTO.response.category.CategoryResponse;
import com.livestream.Service.category.CategoryService;
import com.livestream.Util.PaginationUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("${api.prefix}categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CategoryResponse> updateCategory(
            @PathVariable int id,
            @RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(id, request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<CategoryResponse> getCategory(@PathVariable int id) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategory(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getAllCategoriesNoPagination() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllCategories())
                .build();
    }

    @GetMapping("/list")
    ApiResponse<List<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<CategoryResponse> pageData = categoryService.getAllCategories(pageable);
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(pageData.getContent())
                .page(PaginationUtil.buildPageCustom(pageData, page))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<Void>builder().build();
    }
}
