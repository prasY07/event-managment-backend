package com.example.event_managment.app.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/app/event")
public class EventController {

    @PostMapping("/information")
    public void getEventAndUserInformation()
    {
        
    }
}
