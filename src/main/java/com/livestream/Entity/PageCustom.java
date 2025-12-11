package com.livestream.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageCustom {
    String total_items;
    String total_items_per_page;
    String current_page;
    String total_pages;
}
