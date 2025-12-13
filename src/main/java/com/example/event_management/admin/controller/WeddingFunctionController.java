package com.example.event_management.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.admin.service.impl.WeddingFunctionService;

@RestController
@RequestMapping("/api/admin/wedding/function")
public class WeddingFunctionController {

    @Autowired
    WeddingFunctionService weddingFunctionService;
    
}
