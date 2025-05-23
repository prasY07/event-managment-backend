package com.example.event_managment.admin.service.impl;

import com.example.event_managment.admin.dto.EventDto;
import com.example.event_managment.admin.dto.EventUpdateStatusRequest;
import com.example.event_managment.admin.dto.response.EventResponse;
import com.example.event_managment.admin.dto.response.UserShortResponse;
import com.example.event_managment.common.entity.Event;
import com.example.event_managment.common.entity.EventAccessType;
import com.example.event_managment.common.entity.EventMemberType;
import com.example.event_managment.common.entity.User;
import com.example.event_managment.admin.repository.IEventAccessType;
import com.example.event_managment.admin.repository.IEventMemberType;
import com.example.event_managment.admin.repository.IEventRepo;
import com.example.event_managment.admin.repository.IUserRepo;
import com.example.event_managment.common.AppStatus;
import com.example.event_managment.common.helpers.EventHelper;
import com.example.event_managment.common.helpers.FileStorageHelper;
import com.example.event_managment.common.helpers.UrlHelper;
import com.example.event_managment.common.response.PaginationResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    IEventRepo iEventRepo;

    @Autowired
    IUserRepo iUserRepo;

    @Autowired
    IEventMemberType iEventMemberType;

    @Autowired
    IEventAccessType iEventAccessType;

    public PaginationResponse<List<EventResponse>> getAllEvents(int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<Event> eventPage = iEventRepo.findAll(pageable);

        List<EventResponse> events = eventPage.getContent()
                .stream()
                .map(this::createResponse)
                .toList();

        return new PaginationResponse<>(
                events,
                page,
                size,
                eventPage.getTotalElements(),
                eventPage.getTotalPages()
        );
    }

    public  EventResponse eventInfo(Long id){
        Event event = iEventRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        return createResponse(event);
    }

    public  EventResponse eventInfoWithEventUniqueId(String eventId){
        Event event = iEventRepo.findByEventUUID(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        return createResponse(event);
    }


    public EventResponse createNewEvent (EventDto eventDto)
    {
        User user = iUserRepo.findById(eventDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String uniqueEventId = UUID.randomUUID().toString(); // or your custom logic

        // Create and populate Event entity
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setVenue(eventDto.getVenue());
        event.setAddress(eventDto.getAddress());
        event.setCategory(eventDto.getCategory());
        event.setDescription(eventDto.getDescription());
        event.setPrivacyPolicy(eventDto.getPrivacyPolicy());
        event.setUser(user);
        event.setEventId(uniqueEventId);

        // Save to DB
        Event savedEvent = iEventRepo.save(event);
        Long eventId = savedEvent.getId();

       List<String> memberTypes =  EventHelper.parseUniqueCommaSeparatedValues(eventDto.getEventMemberType());
        for (String memberTypeName : memberTypes) {
            EventMemberType eventMemberType = new EventMemberType();
            eventMemberType.setEventId(savedEvent); // Set full Event object
            eventMemberType.setMemberTypeName(memberTypeName);
            iEventMemberType.save(eventMemberType);
        }

        List<String> accessTypes =  EventHelper.parseUniqueCommaSeparatedValues(eventDto.getEventAccessType());
        for (String accessType : accessTypes) {
            EventAccessType eventAccessType = new EventAccessType();
            eventAccessType.setEventId(savedEvent); // Set full Event object
            eventAccessType.setEventAccessTypeName(accessType);
            iEventAccessType.save(eventAccessType);
        }
        return createResponse(savedEvent);
    }


    public EventResponse updateEventStatus(Long id, EventUpdateStatusRequest newStatus) {
        Event event = iEventRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if (event.getEventStatus() == AppStatus.EventStatus.COMPLETED) {
            throw new IllegalStateException("You cannot update the status as the event is already completed");
        }

        event.setEventStatus(newStatus.getEventStatus());
        iEventRepo.save(event);

        return  createResponse(event);

    }

    public EventResponse updateEvent (Long id, EventDto eventDto)
    {
        User user = iUserRepo.findById(eventDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Event event = iEventRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        if (event.getEventStatus() == AppStatus.EventStatus.ONGOING ||
                event.getEventStatus() == AppStatus.EventStatus.COMPLETED ||
                event.getEventStatus() == AppStatus.EventStatus.CANCELLED) {

            throw new IllegalStateException("You cannot update the event as it is already " + event.getEventStatus() + " .!");
        }

        // Create and populate Event entity

        event.setTitle(eventDto.getTitle());
        event.setStartDate(eventDto.getStartDate());
        event.setEndDate(eventDto.getEndDate());
        event.setVenue(eventDto.getVenue());
        event.setAddress(eventDto.getAddress());
        event.setPrivacyPolicy(eventDto.getPrivacyPolicy());
        event.setCategory(eventDto.getCategory());
        event.setDescription(eventDto.getDescription());
        event.setUser(user);

        // Save to DB
        Event savedEvent = iEventRepo.save(event);

        if (eventDto.getEventMemberType() != null) {
            List<String> memberTypes =  EventHelper.parseUniqueCommaSeparatedValues(eventDto.getEventMemberType());
            for (String memberTypeName : memberTypes) {
                EventMemberType eventMemberType = new EventMemberType();
                eventMemberType.setEventId(savedEvent); // Set full Event object
                eventMemberType.setMemberTypeName(memberTypeName);
                iEventMemberType.save(eventMemberType);
            }
        }

        if (eventDto.getEventAccessType() != null) {

            List<String> accessTypes = EventHelper.parseUniqueCommaSeparatedValues(eventDto.getEventAccessType());
            for (String accessType : accessTypes) {
                EventAccessType eventAccessType = new EventAccessType();
                eventAccessType.setEventId(savedEvent); // Set full Event object
                eventAccessType.setEventAccessTypeName(accessType);
                iEventAccessType.save(eventAccessType);
            }
        }

        return createResponse(savedEvent);
    }


    public EventResponse uploadBanner(Long eventId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }


        Event event = iEventRepo.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        String oldPath = event.getImage(); // Assuming 'image' stores relative path like "uploads/event_8/banner/xyz.png"
        if (oldPath != null) {
            Path oldFilePath = Paths.get(oldPath);
            if (Files.exists(oldFilePath)) {
                try {
                    Files.delete(oldFilePath);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to delete old banner image", e);
                }
            }
        }

        // Save image and get relative path
        String bannerPath = FileStorageHelper.saveImageForEvent(file, eventId, "banner");

        event.setImage(bannerPath); // Make sure 'image' field exists in Event entity
        Event updatedEvent = iEventRepo.save(event);

        return  createResponse(updatedEvent);
    }

    private EventResponse createResponse(Event event) {
        User user = event.getUser(); // assuming this is already fetched and not null

        UserShortResponse userResponse = new UserShortResponse(
                user.getId(),
                user.getName()
        );


        String imageUrl = (event.getImage() != null && !event.getImage().isEmpty())
                ? UrlHelper.imageUrl(event.getImage())
                : null; // Set imageUrl to null if the image is null or empty
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getStartDate(),
                event.getEndDate(),
                event.getVenue(),
                event.getAddress(),
                event.getCategory(),
                event.getDescription(),
                event.getPrivacyPolicy(),
                event.getEventId(),
                imageUrl,
                event.getEventStatus(),
                event.getStatus(),
                userResponse
        );
    }

}
