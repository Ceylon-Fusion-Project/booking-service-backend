package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.EventDTO;
import com.ceylone_fusion.booking_service.dto.request.EventSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.EventGetResponseDTO;

import java.util.List;

public interface EventService {

    EventDTO saveEvent(EventSaveRequestDTO eventSaveRequestDTO);

    List<EventGetResponseDTO> getAllEvents();

    List<EventGetResponseDTO> getAllEventDetailsById(Long eventId);

    List<EventGetResponseDTO> getEventDetailsByExperienceId(Long experienceId);

    String deleteEventById(Long eventId);
}
