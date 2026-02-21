package com.example.event_management.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.admin.dto.AdminAuthDto;
import com.example.event_management.admin.dto.response.AuthResponse;
import com.example.event_management.admin.service.impl.AuthService;
import com.example.event_management.common.response.ApiResponse;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/admin/auth")
public class AuthController {
    
    @Autowired
    AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AdminAuthDto adminAuthDto) {
        System.out.println(passwordEncoder.encode("Admin@!@#$"));
//        $2a$10$WX48QmkAsF/.LCbncsT8x.L3tcOCMALdztI4Ji6crsUeeM2.sGe0m
        AuthResponse admin = authService.adminAuth(adminAuthDto);
       return ApiResponse.success("Login",admin);
    }
}
