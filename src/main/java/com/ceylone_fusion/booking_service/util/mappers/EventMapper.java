package com.ceylone_fusion.booking_service.util.mappers;

import com.ceylone_fusion.booking_service.dto.EventDTO;
import com.ceylone_fusion.booking_service.dto.response.EventGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Event;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    List<EventDTO> EventEntityListToEventDTOList(Page<Event> events);

    List<EventGetResponseDTO> eventDTOListToEventGetResponseDTOList(List<EventDTO> eventDTOS);

}
