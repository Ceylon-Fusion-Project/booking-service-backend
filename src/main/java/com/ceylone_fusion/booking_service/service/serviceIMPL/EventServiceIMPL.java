package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.EventDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedEventGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.EventSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.EventUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.EventGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Event;
import com.ceylone_fusion.booking_service.repo.EventRepo;
import com.ceylone_fusion.booking_service.repo.ExperienceCenterRepo;
import com.ceylone_fusion.booking_service.service.EventService;
import com.ceylone_fusion.booking_service.util.mappers.EventMapper;
import com.ceylone_fusion.booking_service.util.specifications.EventSpecifications;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
//import java.time.LocalTime;
import java.util.List;

@Service
public class EventServiceIMPL implements EventService {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExperienceCenterRepo experienceCenterRepo;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public EventDTO saveEvent(EventSaveRequestDTO eventSaveRequestDTO) {
        Long experienceId = eventSaveRequestDTO.getExperienceId();
        if (experienceCenterRepo.existsById(experienceId)) {
            Event newEvent = new Event(
                    eventSaveRequestDTO.getEventName(),
                    eventSaveRequestDTO.getEventImageURLs(),
                    eventSaveRequestDTO.getEventDescription(),
                    eventSaveRequestDTO.getPricePerEvent(),
                    eventSaveRequestDTO.isAvailable(),
                    eventSaveRequestDTO.getStartTime(),
                    eventSaveRequestDTO.getEndTime(),
                    experienceCenterRepo.findExperienceCenterByExperienceIdEquals(experienceId)
            );

            // Set isAvailable to true
            newEvent.setAvailable(true);


            eventRepo.save(newEvent);
            return modelMapper.map(newEvent, EventDTO.class);
        } else {
            throw new RuntimeException("Experience Center Not Found");
        }
    }

    @Override
    public List<EventGetResponseDTO> getAllEvents() {
        List<Event> getAllEvents = eventRepo.findAll();
        if(!getAllEvents.isEmpty()){
            return modelMapper.map(getAllEvents, new TypeToken<List<EventGetResponseDTO>>(){}.getType());
        }
        else{
            throw new RuntimeException("No Events Found");
        }
    }

    @Override
    public PaginatedEventGetResponseDTO getAllEventsPaginated(Pageable pageable) {
        // Fetch paginated event
        Page<Event> eventsPage = eventRepo.findAll(pageable);
        if (eventsPage.hasContent()) {
            // Convert Page<Event> to List<EventGetResponseDTO>
            List<EventGetResponseDTO> eventGetResponseDTOS = eventsPage.getContent().stream()
                    .map(event -> modelMapper.map(event, EventGetResponseDTO.class))
                    .toList();
            // Return paginated response
            return new PaginatedEventGetResponseDTO(
                    eventGetResponseDTOS,
                    eventsPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Events Found");
        }
    }

    @Override
    public List<EventGetResponseDTO> getAllEventDetailsById(Long eventId) {
        List<Event> events = eventRepo.findAllEventsByEventIdEquals(eventId);
        if(!events.isEmpty()) {
            return modelMapper.map(events, new TypeToken<List<EventGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Event Found");
        }
    }

    @Override
    public PaginatedEventGetResponseDTO getAllEventsSorted(boolean isAvailable, Pageable pageable) {
        // Get all events with is available
        Page<Event> events = eventRepo.findAllByIsAvailableEquals(isAvailable, pageable);
        if (!events.isEmpty()) {
            // Map Event Entity List to Event DTO List
            List<EventDTO> eventDTOS = eventMapper.EventEntityListToEventDTOList(events);
            // Map Event DTO List to EventGetResponseDTO List
            List<EventGetResponseDTO> eventGetResponseDTOS = eventMapper.eventDTOListToEventGetResponseDTOList(eventDTOS);
            // Return PaginatedEventGetResponseDTO
            return new PaginatedEventGetResponseDTO(
                    eventGetResponseDTOS,
                    eventRepo.countEventByIsAvailableEquals(isAvailable)
            );
        } else {
            throw new RuntimeException("No Event Found");
        }
    }

    @Override
    public List<EventGetResponseDTO> getEventDetailsByExperienceId(Long experienceId) {
        if (eventRepo.existsByExperienceCenter_ExperienceId(experienceId)) {
            List<Event> events = eventRepo.findEventByExperienceCenterIn(experienceCenterRepo.findEventsByExperienceIdEquals(experienceId));
            if (!events.isEmpty()) {
                return modelMapper.map(events, new TypeToken<List<EventGetResponseDTO>>() {}.getType());
            }
            else {
                throw new RuntimeException("No Event Found");
            }
        } else {
            throw new RuntimeException("Experience Center Not Found");
        }
    }

    @Override
    public PaginatedEventGetResponseDTO getEventsByExperienceIdPaginated(Long experienceId, Pageable pageable) {
        // Fetch paginated events
        Page<Event> eventsPage = eventRepo.findByExperienceCenter_ExperienceId(experienceId, pageable);
        if (eventsPage.hasContent()) {
            // Convert Page<Event> to List<EventGetResponseDTO>
            List<EventGetResponseDTO> eventGetResponseDTOS = eventsPage.getContent().stream()
                    .map(event -> modelMapper.map(event, EventGetResponseDTO.class))
                    .toList();
            // Return paginated response
            return new PaginatedEventGetResponseDTO(
                    eventGetResponseDTOS,
                    eventsPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Events Found");
        }
    }

    @Override
    public EventDTO updateEventDetails(
            EventUpdateRequestDTO eventUpdateRequestDTO,
            Long eventId
    ) {
        //Get event by Event ID
        if (eventRepo.existsById(eventId)) {
            // Get Event by Event ID and Map Event Entity to Event DTO
            Event existingEvent = eventRepo.getReferenceById(eventId);
            // Update Event Image URLs
            if (eventUpdateRequestDTO.getEventImageURLs() != null) {
                existingEvent.setEventImageURLs(eventUpdateRequestDTO.getEventImageURLs());
            }
            // Update Event Description
            if (eventUpdateRequestDTO.getEventDescription() != null) {
                existingEvent.setEventDescription(eventUpdateRequestDTO.getEventDescription());
            }
            // Update Price Per Event
            if (eventUpdateRequestDTO.getPricePerEvent() != null) {
                existingEvent.setPricePerEvent(eventUpdateRequestDTO.getPricePerEvent());
            }
            // Update Is Available
//            if (eventUpdateRequestDTO.isAvailable()) {
//                existingEvent.setAvailable(true);
//            }

            // Set isAvailable default true
            existingEvent.setAvailable(true);

            // Update Event Start Time
            if (eventUpdateRequestDTO.getStartTime() != null) {
                existingEvent.setStartTime(eventUpdateRequestDTO.getStartTime());
            }
            // Update Event End Time
            if (eventUpdateRequestDTO.getEndTime() != null) {
                existingEvent.setEndTime(eventUpdateRequestDTO.getEndTime());
            }
            // Save the updated Event
            eventRepo.save(existingEvent);
            return modelMapper.map(existingEvent, EventDTO.class);
        } else {
            throw new RuntimeException("Event Not Found");
        }
    }

    @Override
    public String deleteEventById(Long eventId) {
        // Get Event by Event ID
        if (eventRepo.existsById(eventId)) {
            String response = eventRepo.getReferenceById(eventId).getEventName() + " Deleted!";
            //delete event
            eventRepo.deleteById(eventId);
            return response;
        } else {
            throw new RuntimeException("Event Not Found");
        }
    }

    @Override
    public PaginatedEventGetResponseDTO getEventByFiltering(
            String eventName,
            Double minPrice,
            Double maxPrice,
            LocalDateTime startTime,
            LocalDateTime endTime,
            boolean isAvailable,
            Pageable pageable
    ) {
        Specification<Event> specification = Specification.
                where(EventSpecifications.isAvailable(isAvailable))
                .and(EventSpecifications.hasName(eventName))
                .and(EventSpecifications.hasPriceRange(minPrice, maxPrice))
                .and(EventSpecifications.hasTimeRange(startTime, endTime));
        // Get all events with available
        Page<Event> events = eventRepo.findAll(specification, pageable);
        if (!events.isEmpty()) {
            // Map Event Entity List to Event DTO List
            List<EventDTO> eventDTOS = eventMapper.EventEntityListToEventDTOList(events);
            // Map Event DTO List to EventGetResponseDTO List
            List<EventGetResponseDTO> eventGetResponseDTOS = eventMapper
                    .eventDTOListToEventGetResponseDTOList(eventDTOS);
            return new PaginatedEventGetResponseDTO(
                    eventGetResponseDTOS,
                    eventRepo.count(specification)
            );
        } else {
            throw new RuntimeException("No Event Found");
        }
    }

}
