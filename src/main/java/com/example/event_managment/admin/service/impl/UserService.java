package com.example.event_managment.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.event_managment.admin.dto.AddUpdateUserDto;
import com.example.event_managment.admin.dto.UserDto;
import com.example.event_managment.admin.dto.response.UserResponse;
import com.example.event_managment.admin.repository.IUserRepo;
import com.example.event_managment.common.helpers.admin.EventHelper;
import com.example.event_managment.common.helpers.admin.UserHelper;
import com.example.event_managment.common.helpers.admin.UserPassword;
import com.example.event_managment.common.response.PaginationResponse;
import com.example.event_managment.entity.User;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    IUserRepo iUserRepo;

    public List<UserResponse> getAllUsers() {
        List<User> userResponse = iUserRepo.findAll();
        return userResponse.stream().map(this::createResponse).toList();
    }

    public UserResponse userInfo(Long id) {
        User user = iUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return createResponse(user);

    }

    public UserResponse createUser(AddUpdateUserDto addUpdateUserDto) {
        if (iUserRepo.findByEmail(addUpdateUserDto.getEmail())) {
            throw new IllegalArgumentException("Email is already taken by another user");
        }

        if (iUserRepo.findByPhoneNumber(addUpdateUserDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Email is already taken by another user");
        }

        String registrationId;

        do {
            registrationId = EventHelper.createUserUniqueRegistrationID();
        } while (iUserRepo.existsByUserRegId(registrationId));

        User user = new User();
        user.setName(addUpdateUserDto.getName());
        user.setEmail(addUpdateUserDto.getEmail());
        user.setPhoneNumber(addUpdateUserDto.getPhoneNumber());
        user.setUserRegId(registrationId);
        user.setPassword(UserPassword.createAndHashPassword());
        User savedUser = iUserRepo.save(user); // Save and get the saved entity with ID
        return createResponse(savedUser); // Convert to UserResponse and return
    }

    public UserResponse updateUser(Long id, UserDto userDto) {

        User user = iUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (iUserRepo.existsByEmailAndIdNot(userDto.getEmail(), id)) {
            throw new IllegalArgumentException("Email is already taken by another user");
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        User savedUser = iUserRepo.save(user);
        return createResponse(savedUser); // Convert to UserResponse and return
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
                userPage.getTotalPages());
    }

    private UserResponse createResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail());
    }
}
