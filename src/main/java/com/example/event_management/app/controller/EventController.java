package com.example.event_management.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.app.service.impl.EventService;

@RestController("appEventController")
@RequestMapping("api/app/event")
public class EventController {

     @Autowired
     EventService eventService; 

     @PostMapping("/{redId}/user-information")
     public void getUserInformation(@PathVariable String redId) {

          eventService.getUserInformation(redId);

     }
}
