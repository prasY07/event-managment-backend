package com.example.event_management.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.AddUpdateUserDto;
import com.example.event_management.admin.dto.UserStatusDto;
import com.example.event_management.admin.dto.response.UserListResponse;
import com.example.event_management.admin.dto.response.UserResponse;
import com.example.event_management.admin.dto.response.UserShortResponse;
import com.example.event_management.common.AppStatus;
import com.example.event_management.common.helpers.admin.UserPassword;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.Country;
import com.example.event_management.entity.User;
import com.example.event_management.repository.ICountryRepo;
import com.example.event_management.repository.IUserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    @Autowired
    IUserRepo iUserRepo;

    @Autowired
    ICountryRepo icountryRepo;

    public List<UserResponse> getAllUsers() {
        List<User> userResponse = iUserRepo.findByStatus(AppStatus.UserStatus.ACTIVE);
        return userResponse.stream().map(this::createResponse).toList();
    }

    public UserResponse userInfo(Long id) {
        User user = iUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return createResponse(user);

    }

    public UserShortResponse createUser(AddUpdateUserDto addUpdateUserDto) {
        if (iUserRepo.existsByEmail(addUpdateUserDto.getEmail())) {
            throw new IllegalArgumentException("Email is already taken by another user");
        }

        if (iUserRepo.existsByPhoneNumber(addUpdateUserDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Email is already taken by another user");
        }
        Country country = icountryRepo.findById(addUpdateUserDto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid country selected"));

//        String registrationId;
//
//        do {
//            registrationId = EventHelper.createUserUniqueRegistrationID();
//        } while (iUserRepo.existsByUserRegId(registrationId));

        User user = new User();
        user.setName(addUpdateUserDto.getName());
        user.setEmail(addUpdateUserDto.getEmail());
        user.setPhoneNumber(addUpdateUserDto.getPhoneNumber());
//        user.setUserRegId(registrationId);
        user.setPassword(UserPassword.createAndHashPassword());
        user.setRole(addUpdateUserDto.getRole());
        user.setCountry(country); // Correct way to set the entire country entity
        User savedUser = iUserRepo.save(user); // Save and get the saved entity with ID
        return createUserShortResponse(savedUser); // Convert to UserResponse and return
    }

    public UserShortResponse updateUser(Long id, AddUpdateUserDto addUpdateUserDto) {

        User user = iUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (iUserRepo.existsByEmailAndIdNot(addUpdateUserDto.getEmail(), id)) {
            throw new IllegalArgumentException("Email is already taken by another user");
        }
        if (iUserRepo.existsByPhoneNumberAndIdNot(addUpdateUserDto.getPhoneNumber(), id)) {
            throw new IllegalArgumentException("Phone Number is already taken by another user");
        }

        user.setName(addUpdateUserDto.getName());
        user.setEmail(addUpdateUserDto.getEmail());
        user.setPhoneNumber(addUpdateUserDto.getPhoneNumber());
        user.setRole(addUpdateUserDto.getRole());
        User savedUser = iUserRepo.save(user);
        return createUserShortResponse(savedUser); // Convert to UserResponse and return
    }

    public PaginationResponse<List<UserListResponse>> getAllUsersWithPagination(int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<User> userPage = iUserRepo.findAll(pageable);

        List<UserListResponse> users = userPage.getContent()
                .stream()
                .map(this::createUserListResponse)
                .toList();

        return new PaginationResponse<>(
                users,
                page,
                size,
                userPage.getTotalElements(),
                userPage.getTotalPages());
    }

    public UserShortResponse updateStatus(Long id, UserStatusDto userStatusDto) {
        User user = iUserRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setStatus(userStatusDto.getStatus());
        User savedUser = iUserRepo.save(user);
        return createUserShortResponse(savedUser);
    }

    private UserResponse createResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    private UserListResponse createUserListResponse(User user) {
        return new UserListResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getStatus());
    }

    private UserShortResponse createUserShortResponse(User user) {
        return new UserShortResponse(
                user.getId(),
                user.getName());
    }
}
