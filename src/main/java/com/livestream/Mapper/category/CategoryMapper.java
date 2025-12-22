package com.livestream.Mapper.category;

import com.livestream.DTO.request.category.CategoryRequest;
import com.livestream.DTO.response.category.CategoryResponse;
import com.livestream.Entity.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
