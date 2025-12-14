package com.example.event_management.admin.controller;

import com.example.event_management.admin.dto.WeddingFunctionRequest;
import com.example.event_management.admin.dto.response.WeddingFunctionResponse;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.projection.admin.WeddingFunctionProjection;
import com.example.event_management.projection.admin.WeddingListShortProjection;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.event_management.admin.service.impl.WeddingFunctionService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/wedding/function")
public class WeddingFunctionController {

    @Autowired
    WeddingFunctionService weddingFunctionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Long>> addFunction(
            @Valid @RequestBody WeddingFunctionRequest request) {

        Long functionId = weddingFunctionService.addWeddingFunction(request);
        return ApiResponse.success("Wedding function event created Successfully", functionId);
    }

    @PutMapping("/{functionId}/update")
    public ResponseEntity<ApiResponse<String>> updateFunction(
            @PathVariable Long functionId,
            @Valid @RequestBody WeddingFunctionRequest request) {

        weddingFunctionService.updateWeddingFunction(functionId, request);
        return ApiResponse.success("Wedding function event updated Successfully", null);

    }

    @DeleteMapping("/{functionId}")
    public ResponseEntity<ApiResponse<String>> deleteFunction(
            @PathVariable Long functionId) {

        weddingFunctionService.deleteWeddingFunction(functionId);

        return ApiResponse.success("Wedding function event deleted Successfully", null);

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<WeddingFunctionProjection>>>> getAllFunctions(
            @RequestParam Long weddingId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PaginationResponse<List<WeddingFunctionProjection>> paginatedUsers =
                weddingFunctionService.getAllFunctions(weddingId, page, size);

        return ApiResponse.successWithPagination(
                "Wedding Functions List",
                paginatedUsers.getItems(),
                paginatedUsers.getPage(),
                paginatedUsers.getSize(),
                paginatedUsers.getTotalElements(),
                paginatedUsers.getTotalPages()
        );
    }

    @GetMapping("/{functionId}")
    public ResponseEntity<ApiResponse<WeddingFunctionResponse>> getFunctionById(
            @PathVariable Long functionId) {

        WeddingFunctionResponse functionResponse = weddingFunctionService.getFunctionById(functionId);
        return ApiResponse.success("All details for single functions fetched successfully", functionResponse);

    }
}
