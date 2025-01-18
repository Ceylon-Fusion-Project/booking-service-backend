package com.ceylone_fusion.booking_service.util.mappers;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Accommodation;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {
    List<AccommodationDTO> AccommodationEntityListToAccommodationDTOList(Page<Accommodation> accommodations);

    List<AccommodationGetResponseDTO> accommodationDTOListToAccommodationGetResponseDTOList(List<AccommodationDTO> accommodationDTOS);
}
