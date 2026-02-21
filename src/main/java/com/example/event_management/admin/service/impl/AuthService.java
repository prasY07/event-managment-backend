package com.example.event_management.admin.service.impl;
import com.example.event_management.entity.Admin;
import com.example.event_management.repository.IAdminRepo;
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

    private final IAdminRepo iAdminRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(IAdminRepo iAdminRepo, JwtUtil jwtUtil) {
        this.iAdminRepo = iAdminRepo;
        this.jwtUtil = jwtUtil;
    }


     public AuthResponse adminAuth(AdminAuthDto adminAuthDto)
      {
        Admin admin = iAdminRepo.findByEmail(adminAuthDto.getEmail());

        if(admin == null)
        {
             throw new EntityNotFoundException("User not found");

        }

        if (!passwordEncoder.matches(adminAuthDto.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new AuthResponse(
            jwtUtil.generateToken(admin.getId())
        );
    }
}
