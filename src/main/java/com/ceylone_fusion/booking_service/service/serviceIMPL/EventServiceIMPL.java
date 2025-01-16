package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.EventDTO;
import com.ceylone_fusion.booking_service.dto.request.EventSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.EventGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Event;
import com.ceylone_fusion.booking_service.repo.EventRepo;
import com.ceylone_fusion.booking_service.repo.ExperienceCenterRepo;
import com.ceylone_fusion.booking_service.service.EventService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceIMPL implements EventService {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExperienceCenterRepo experienceCenterRepo;

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

            eventRepo.save(newEvent);
            return modelMapper.map(newEvent, EventDTO.class);
        } else {
            throw new RuntimeException("Experience Center Not Found");
        }
    }

    @Override
    public List<EventGetResponseDTO> getAllEventDetailsById(Long eventId) {
        List<Event> events = eventRepo.findAllEventsByEventIdEquals(eventId);
        if(events.size() > 0) {
            List<EventGetResponseDTO> eventGetResponseDTOS = modelMapper.map(events, new TypeToken<List<EventGetResponseDTO>>(){}.getType());
            return eventGetResponseDTOS;
        }
        else {
            throw new RuntimeException("No Event Found");
        }
    }


    @Override
    public List<EventGetResponseDTO> getAllEvents() {
        List<Event> getAllEvents = eventRepo.findAll();
        if(getAllEvents.size() > 0){
            List<EventGetResponseDTO> eventGetResponseDTOS = modelMapper.map(getAllEvents, new TypeToken<List<EventGetResponseDTO>>(){}.getType());
            return eventGetResponseDTOS;
        }
        else{
            throw new RuntimeException("No Events Found");
        }
    }


    @Override
    public List<EventGetResponseDTO> getEventDetailsByExperienceId(Long experienceId) {
        if (eventRepo.existsByExperienceCenter_ExperienceId(experienceId)) {
            List<Event> events = eventRepo.findEventByExperienceCenterIn(experienceCenterRepo.findEventsByExperienceIdEquals(experienceId));
            if (events.size() > 0) {
                List<EventGetResponseDTO> eventGetResponseDTOS = modelMapper.map(events, new TypeToken<List<EventGetResponseDTO>>() {
                }.getType());
                return eventGetResponseDTOS;
            }
            else {
                throw new RuntimeException("No Event Found");
            }
        } else {
            throw new RuntimeException("Experience Center Not Found");
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

}
