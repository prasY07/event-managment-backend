package com.example.event_management.admin.service.impl;

import com.example.event_management.admin.dto.WeddingFunctionRequest;
import com.example.event_management.admin.dto.response.WeddingFunctionResponse;
import com.example.event_management.common.response.PaginationResponse;
import com.example.event_management.entity.WeddingFunction;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.entity.WeddingSideMaster;
import com.example.event_management.projection.admin.WeddingFunctionProjection;
import com.example.event_management.repository.IWeddingFunctionRepo;
import com.example.event_management.repository.IWeddingMasterRepo;
import com.example.event_management.repository.IWeddingSideMasterRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WeddingFunctionService {

    private final IWeddingFunctionRepo weddingFunctionRepository;
    private final IWeddingMasterRepo weddingMasterRepository;
    private final IWeddingSideMasterRepo weddingSideMasterRepository;

    public Long addWeddingFunction(WeddingFunctionRequest request) {

        WeddingMaster weddingMaster = weddingMasterRepository.findById(request.getWeddingId())
                .orElseThrow(() -> new EntityNotFoundException("Wedding not found"));

        WeddingSideMaster side = weddingSideMasterRepository.findById(request.getSideId())
                .orElseThrow(() -> new EntityNotFoundException("Wedding side not found"));

        WeddingFunction function = WeddingFunction.builder()
                .weddingMaster(weddingMaster)
                .side(side)
                .functionName(request.getFunctionName())
                .functionDate(request.getFunctionDate())
                .functionStartTime(request.getFunctionStartTime())
                .functionEndTime(request.getFunctionEndTime())
                .venueName(request.getVenueName())
                .venueAddress(request.getVenueAddress())
                .city(request.getCity())
                .build();

        return weddingFunctionRepository.save(function).getFunctionId();
    }

    public void updateWeddingFunction(Long functionId, WeddingFunctionRequest request) {

        WeddingFunction function = weddingFunctionRepository.findById(functionId)
                .orElseThrow(() -> new EntityNotFoundException("Wedding function not found"));

        function.setFunctionName(request.getFunctionName());
        function.setFunctionDate(request.getFunctionDate());
        function.setFunctionStartTime(request.getFunctionStartTime());
        function.setFunctionEndTime(request.getFunctionEndTime());
        function.setVenueName(request.getVenueName());
        function.setVenueAddress(request.getVenueAddress());
        function.setCity(request.getCity());
    }

    public void deleteWeddingFunction(Long functionId) {

        WeddingFunction function = weddingFunctionRepository.findById(functionId)
                .orElseThrow(() -> new EntityNotFoundException("Wedding function not found"));

        function.setIsDeleted(true);
        function.setDeletedAt(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public PaginationResponse<List<WeddingFunctionProjection>> getAllFunctions(
            Long weddingId,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("functionDate").ascending());

        Page<WeddingFunctionProjection> result =
                weddingFunctionRepository.findByWeddingMaster_WeddingId(weddingId, pageable);

        return new PaginationResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Transactional(readOnly = true)
    public WeddingFunctionResponse getFunctionById(Long functionId) {

        WeddingFunction function = weddingFunctionRepository.findById(functionId)
                .orElseThrow(() -> new EntityNotFoundException("Wedding function not found"));

        return WeddingFunctionResponse.builder()
                .functionId(function.getFunctionId())
                .weddingId(function.getWeddingMaster().getWeddingId())
                .sideId(function.getSide().getSideId())
                .sideName(function.getSide().getSideName())
                .functionName(function.getFunctionName())
                .functionDate(function.getFunctionDate())
                .functionStartTime(function.getFunctionStartTime())
                .functionEndTime(function.getFunctionEndTime())
                .venueName(function.getVenueName())
                .venueAddress(function.getVenueAddress())
                .city(function.getCity())
                .createdAt(function.getCreatedAt())
                .updatedAt(function.getUpdatedAt())
                .build();
    }
}

