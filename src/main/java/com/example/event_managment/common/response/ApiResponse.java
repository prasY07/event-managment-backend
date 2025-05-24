package com.example.event_managment.common.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message, T data, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponse<>(false, message, data), status);
    }
    public static <T> ResponseEntity<ApiResponse<PaginationResponse<T>>> successWithPagination(
            String message,
            T items,
            int page,
            int size,
            long totalElements,
            int totalPages
    ) {
        PaginationResponse<T> paginationData = new PaginationResponse<>(items, page, size, totalElements, totalPages);
        return ResponseEntity.ok(new ApiResponse<>(true, message, paginationData));
    }
}
