package com.example.event_managment.admin.service.impl;

import com.example.event_managment.admin.dto.UserDto;
import com.example.event_managment.admin.dto.response.UserResponse;
import com.example.event_managment.entity.User;
import com.example.event_managment.admin.repository.IUserRepo;
import com.example.event_managment.common.response.PaginationResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import java.awt.print.Pageable;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo iUserRepo;

    public List<UserResponse> getAllUsers()
    {
        List<User> userResponse = iUserRepo.findAll();
        return userResponse.stream().map(this::createResponse).toList();
    }

    public UserResponse userInfo(Long id)
    {
        User user = iUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return createResponse(user);

    }

    public UserResponse createUser(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        User savedUser = iUserRepo.save(user);  // Save and get the saved entity with ID
        return createResponse(savedUser);       // Convert to UserResponse and return
    }

    public UserResponse updateUser(Long id, UserDto userDto){

        User user = iUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (iUserRepo.existsByEmailAndIdNot(userDto.getEmail(), id)) {
            throw new IllegalArgumentException("Email is already taken by another user");
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        User savedUser = iUserRepo.save(user);
        return createResponse(savedUser);       // Convert to UserResponse and return
    }

    public PaginationResponse<List<UserResponse>> getAllUsersWithPagination(int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<User> userPage = iUserRepo.findAll(pageable);

        List<UserResponse> users = userPage.getContent()
                .stream()
                .map(this::createResponse)
                .toList();

        return new PaginationResponse<>(
                users,
                page,
                size,
                userPage.getTotalElements(),
                userPage.getTotalPages()
        );
    }

    private UserResponse createResponse(User user)
    {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
