package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;

import java.util.List;

public interface AccommodationService {
    AccommodationDTO saveAccommodation(AccommodationSaveRequestDTO accommodationSaveRequestDTO);

    List<AccommodationGetResponseDTO> getAllAccommodations();

}
