package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.EventDTO;
import com.ceylone_fusion.booking_service.dto.request.EventSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.EventGetResponseDTO;
import com.ceylone_fusion.booking_service.service.EventService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/event")
@CrossOrigin
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping(path = "/save-event")
    public ResponseEntity<StandardResponse> saveEvent(@RequestBody EventSaveRequestDTO eventSaveRequestDTO) {
        try {
            EventDTO response = eventService.saveEvent(eventSaveRequestDTO);

            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(201, "Event Saved Successfully", response.getEventId()),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(409, e.getMessage(), null),
                    HttpStatus.CONFLICT
            );
        }
    }


    @GetMapping(path = "/get-all-events")
    public ResponseEntity<StandardResponse> getAllEvents() {
        try {
            List<EventGetResponseDTO> allEvents = eventService.getAllEvents();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Success", allEvents),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }

    }

    @GetMapping(
            path = "/get-event-details-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> getEventById(@RequestParam(value = "id") Long eventId) {
        try {
            List<EventGetResponseDTO> response = eventService.getAllEventDetailsById(eventId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Event Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @GetMapping(path = "/get-events-by-experience-id")
    public ResponseEntity<StandardResponse> getEventsByExperienceId(@RequestParam(value = "id") Long experienceId) {
        try {
            List<EventGetResponseDTO> response = eventService.getEventDetailsByExperienceId(experienceId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Event Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @DeleteMapping(
            path = "/delete-event-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> deleteEventById(@RequestParam(value = "id") Long eventId) {
        try {
            String response = eventService.deleteEventById(eventId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, response, "Event Deleted Successfully"),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

}
