package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.EventDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedEventGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.EventSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.EventUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.EventGetResponseDTO;
import com.ceylone_fusion.booking_service.service.EventService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
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
            path = "/get-all-events-by-available",
            params = {"page", "size", "isAvailable"}
    )
    public ResponseEntity<StandardResponse> getAllEventWithSort(
            @RequestParam(value = "isAvailable",defaultValue = "true",required = false) boolean isAvailable,
            @RequestParam(value = "sort",required = false,defaultValue = "nameAsc") String sort,
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10",required = false) Integer size
    ) {
        try {
            // Sort Specification
            Sort sortSpec;
            switch (sort) {
                case "nameAsc":
                    sortSpec = Sort.by("eventName").ascending();
                    break;
                case "nameDesc":
                    sortSpec = Sort.by("eventName").descending();
                    break;
                case "priceAsc":
                    sortSpec = Sort.by("pricePerEvent").ascending();
                    break;
                case "priceDesc":
                    sortSpec = Sort.by("pricePerEvent").descending();
                    break;
                case "timeAsc":
                    sortSpec = Sort.by("startTime").ascending();
                    break;
                case "timeDesc":
                    sortSpec = Sort.by("startTime").descending();
                    break;
                default:
                    sortSpec = Sort.by("eventName").ascending();
            }

            // Page Request Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);

            PaginatedEventGetResponseDTO response = eventService.getAllEventsSorted(isAvailable, pageRequest);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "All Events", response),
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


    @PatchMapping(
            path = "/update-event-details",
            params = "id"
    )
    public ResponseEntity<StandardResponse> updateEventDetails(
            @RequestBody EventUpdateRequestDTO eventUpdateRequestDTO,
            @RequestParam(value = "id") Long eventId
    ) {
        try {
            EventDTO response = eventService.updateEventDetails(eventUpdateRequestDTO, eventId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Event Updated Successfully", response.getEventName()),
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


    @GetMapping(path = "/get-event-by-filtering")
    public ResponseEntity<StandardResponse> getEventByFiltering(
            @RequestParam(required = false) String eventName,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false,defaultValue = "true" ) boolean isAvailable,
            @RequestParam(value = "sort",required = false,defaultValue = "nameAsc") String sort,
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer size
    ) {
        try {
            // Parse startTime and endTime if provided
            LocalTime parsedStartTime = startTime != null ? LocalTime.parse(startTime) : null;
            LocalTime parsedEndTime = endTime != null ? LocalTime.parse(endTime) : null;

            // Sort Specification
            Sort sortSpec;
            switch (sort) {
                case "nameAsc":
                    sortSpec = Sort.by("eventName").ascending();
                    break;
                case "nameDesc":
                    sortSpec = Sort.by("eventName").descending();
                    break;
                case "priceAsc":
                    sortSpec = Sort.by("pricePerEvent").ascending();
                    break;
                case "priceDesc":
                    sortSpec = Sort.by("pricePerEvent").descending();
                    break;
                case "timeAsc":
                    sortSpec = Sort.by("startTime").ascending();
                    break;
                case "timeDesc":
                    sortSpec = Sort.by("startTime").descending();
                    break;
                default:
                    sortSpec = Sort.by("eventName").ascending();
            }

            // Page Request Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);
            PaginatedEventGetResponseDTO response = eventService.getEventByFiltering(eventName, minPrice, maxPrice, parsedStartTime, parsedEndTime, isAvailable, pageRequest);
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

}
