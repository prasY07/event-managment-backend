package com.example.event_management.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_management.app.dto.ServiceUsagesDto;
import com.example.event_management.app.dto.response.EventDayServiceListResponse;
import com.example.event_management.app.dto.response.UserResponse;
import com.example.event_management.app.service.impl.EventService;
import com.example.event_management.common.response.ApiResponse;

@RestController("appEventController")
@RequestMapping("api/app/event")
public class EventController {

     @Autowired
     EventService eventService; 

     @GetMapping("/{redId}/user-information")
     public ResponseEntity<ApiResponse<UserResponse>> getUserInformation(@PathVariable String redId) {

       UserResponse res =    eventService.getUserInformation(redId);
       return ApiResponse.success("Information", res);

     }

     @GetMapping("/get-day-service")
     public ResponseEntity<ApiResponse<List<EventDayServiceListResponse>>> getDayService(
           @RequestParam LocalDate date,
            @RequestParam Long eventid,
            @RequestParam Long memberTypeId
     )
     {
          List<EventDayServiceListResponse> res =   eventService.getEventDayService(date,eventid,memberTypeId);   
          return ApiResponse.success("All List",res);
     }

     @PostMapping ResponseEntity<ApiResponse<String>> markServiceUsages(@RequestBody ServiceUsagesDto serviceUsagesDto)
     {
              Boolean  res =    eventService.checkAndMarkServiceUsages();
          return ApiResponse.success("All List",null);
     }
}
