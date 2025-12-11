package com.livestream.Util;

import org.springframework.data.domain.Page;

import com.livestream.Entity.PageCustom;

public class PaginationUtil {

    public static <T> PageCustom buildPageCustom(Page<T> page, int pageNumber) {
        return PageCustom.builder()
                .total_pages(String.valueOf(page.getTotalPages()))
                .total_items(String.valueOf(page.getTotalElements()))
                .total_items_per_page(String.valueOf(page.getNumberOfElements()))
                .current_page(String.valueOf(pageNumber))
                .build();
    }
}
