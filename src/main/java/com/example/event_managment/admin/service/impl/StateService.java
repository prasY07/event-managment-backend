package com.example.event_managment.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event_managment.admin.dto.response.StateShortResponse;
import com.example.event_managment.entity.State;
import com.example.event_managment.repository.IStateRepo;

@Service
public class StateService {

    @Autowired
    IStateRepo iStateRepo;

    public List<StateShortResponse> getAllStates() {
        List<State> states = iStateRepo.findAll();
        return states.stream().map(this::createResponse).toList();
    }

    private StateShortResponse createResponse(State state) {
        return new StateShortResponse(
                state.getId(),
                state.getName());
    }

}
