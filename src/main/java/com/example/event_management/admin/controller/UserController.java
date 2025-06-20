package com.example.event_management.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.admin.dto.AddUpdateUserDto;
import com.example.event_management.admin.dto.UserStatusDto;
import com.example.event_management.admin.dto.response.UserListResponse;
import com.example.event_management.admin.dto.response.UserResponse;
import com.example.event_management.admin.dto.response.UserShortResponse;
import com.example.event_management.admin.service.impl.UserService;
import com.example.event_management.common.response.ApiResponse;
import com.example.event_management.common.response.PaginationResponse;

@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user-list")
    public ResponseEntity<ApiResponse<PaginationResponse<List<UserListResponse>>>> allUsersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginationResponse<List<UserListResponse>> paginatedUsers = userService.getAllUsersWithPagination(page, size);
        return ApiResponse.successWithPagination(
                "User List",
                paginatedUsers.getItems(),
                paginatedUsers.getPage(),
                paginatedUsers.getSize(),
                paginatedUsers.getTotalElements(),
                paginatedUsers.getTotalPages());
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<UserResponse>>> allUsers() {
        List<UserResponse> allUsers = userService.getAllUsers();
        return ApiResponse.success("User List", allUsers);
        
    }

    @GetMapping("{id}/user-information")
    public ResponseEntity<ApiResponse<UserResponse>> userInfo(@PathVariable Long id) {
        UserResponse allUsers = userService.userInfo(id);
        return ApiResponse.success("User Information", allUsers);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserShortResponse>> createUser(@RequestBody AddUpdateUserDto addUpdateUserDto) {
        UserShortResponse newUser = userService.createUser(addUpdateUserDto);
        return ApiResponse.success("New User Created successfully", newUser);
    }

    @PutMapping("{id}/update")
    public ResponseEntity<ApiResponse<UserShortResponse>> updateUser(@PathVariable Long id,
            @RequestBody AddUpdateUserDto addUpdateUserDto) {
        UserShortResponse updatedUser = userService.updateUser(id, addUpdateUserDto);
        return ApiResponse.success("User updated successfully", updatedUser);
    }

    @PatchMapping("{id}/update-status")
    public ResponseEntity<ApiResponse<UserShortResponse>> updateUserStatus(@PathVariable Long id,
            @RequestBody UserStatusDto userStatusDto) {
        UserShortResponse updatedUser = userService.updateStatus(id, userStatusDto);
        return ApiResponse.success("User Status updated successfully", updatedUser);
    }

}
