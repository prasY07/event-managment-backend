package com.example.event_management.commonapi.service.impl;

import com.example.event_management.commonapi.dto.response.StateShortResponse;
import com.example.event_management.entity.State;
import com.example.event_management.repository.IStateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
