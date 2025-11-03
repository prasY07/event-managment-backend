package com.example.event_management.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.event_management.admin.service.impl.WedService;

@RequestMapping("/api/admin/wedding")
public class WedController {

    @Autowired
    WedService wedService;

    
}
