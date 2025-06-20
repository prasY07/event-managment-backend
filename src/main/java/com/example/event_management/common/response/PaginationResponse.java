package com.example.event_management.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationResponse<T> {
    private T items;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
