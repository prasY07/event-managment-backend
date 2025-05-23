package com.example.event_managment.admin.service.impl;

import com.example.event_managment.admin.repository.IEventRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventRegistrationService {

    @Autowired
    IEventRegistration iEventRegistration;
}
