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
    String totalItems;
    String totalItemsPerPage;
    String currentPage;
    String totalPages;
}
