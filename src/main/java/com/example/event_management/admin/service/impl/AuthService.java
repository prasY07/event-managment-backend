package com.example.event_management.admin.service.impl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.event_management.admin.dto.AdminAuthDto;
import com.example.event_management.admin.dto.response.AuthResponse;
import com.example.event_management.common.AppStatus;
import com.example.event_management.common.jwt.JwtUtil;
import com.example.event_management.entity.User;
import com.example.event_management.repository.IUserRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthService {

    private final IUserRepo iUserRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(IUserRepo iUserRepo, JwtUtil jwtUtil) {
        this.iUserRepo = iUserRepo;
        this.jwtUtil = jwtUtil;
    }


     public AuthResponse userAuth(AdminAuthDto adminAuthDto)
      {
        User user = iUserRepo.getUserByEmailAndRole(adminAuthDto.getEmail(),AppStatus.UserRole.MASTER_ADMIN.name());

        if(user == null)
        {
             throw new EntityNotFoundException("User not found");

        }

        if (!passwordEncoder.matches(adminAuthDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new AuthResponse(
            jwtUtil.generateToken(user.getId())
        );
    }
}
