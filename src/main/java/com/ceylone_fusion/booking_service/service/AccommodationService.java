package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;

import java.util.List;

public interface AccommodationService {
    AccommodationDTO saveAccommodation(AccommodationSaveRequestDTO accommodationSaveRequestDTO);

    List<AccommodationGetResponseDTO> getAllAccommodations();

    List<AccommodationGetResponseDTO> getAccommodationById(Long accommodationId);

    List<AccommodationGetResponseDTO> getAccommodationByCode(String accommodationCode);

    AccommodationDTO updateAccommodationDetails(AccommodationUpdateRequestDTO accommodationUpdateRequestDTO, Long accommodationId);

    String deleteAccommodationById(Long accommodationId);

}
