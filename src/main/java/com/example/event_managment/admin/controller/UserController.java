package com.example.event_managment.admin.controller;

import com.example.event_managment.admin.dto.UserDto;
import com.example.event_managment.admin.dto.response.UserResponse;
import com.example.event_managment.admin.service.impl.UserService;
import com.example.event_managment.common.response.ApiResponse;
import com.example.event_managment.common.response.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
public class UserController {

    @Autowired
    UserService userService;

//    @GetMapping("/user-list")
//    public ResponseEntity<ApiResponse<PaginationResponse<List<UserResponse>>>> allUsersWithPagination(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    )
//    {
//        PaginationResponse<List<UserResponse>> paginatedUsers = userService.getAllUsersWithPagination(page, size);
//        return ApiResponse.successWithPagination(
//                "User List",
//                paginatedUsers.getItems(),
//                paginatedUsers.getPage(),
//                paginatedUsers.getSize(),
//                paginatedUsers.getTotalElements(),
//                paginatedUsers.getTotalPages()
//        );
//    }


    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<UserResponse>>> allUsers()
    {
        List<UserResponse> allUsers = userService.getAllUsers();
        return ApiResponse.success("User List", allUsers);
    }

    @GetMapping("{id}/user-information")
    public ResponseEntity<ApiResponse<UserResponse>> userInfo(@PathVariable Long id)
    {
        UserResponse allUsers = userService.userInfo(id);
        return ApiResponse.success("User Information", allUsers);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserDto userDto)
    {
        UserResponse newUser = userService.createUser(userDto);
        return ApiResponse.success("New User Created successfully", newUser);
    }

    @PutMapping("{id}/update")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserDto userDto)
    {
        UserResponse updatedUser = userService.updateUser(id, userDto);
        return ApiResponse.success("User updated successfully", updatedUser);
    }

}
