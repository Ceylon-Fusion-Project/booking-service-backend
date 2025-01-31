package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.EventDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedEventGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.EventSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.EventUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.EventGetResponseDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;

public interface EventService {

    EventDTO saveEvent(EventSaveRequestDTO eventSaveRequestDTO);

    List<EventGetResponseDTO> getAllEvents();

    List<EventGetResponseDTO> getAllEventDetailsById(Long eventId);

    List<EventGetResponseDTO> getEventDetailsByExperienceId(Long experienceId);

    String deleteEventById(Long eventId);

    PaginatedEventGetResponseDTO getAllEventsSorted(boolean isAvailable, Pageable pageable);

    PaginatedEventGetResponseDTO getEventByFiltering(String eventName, Double minPrice, Double maxPrice, LocalTime startTime, LocalTime endTime, boolean isAvailable, Pageable pageable);

    EventDTO updateEventDetails(EventUpdateRequestDTO eventUpdateRequestDTO, Long eventId);

    PaginatedEventGetResponseDTO getAllEventsPaginated(Pageable pageable);

    PaginatedEventGetResponseDTO getEventsByExperienceIdPaginated(Long experienceId, Pageable pageable);

}
