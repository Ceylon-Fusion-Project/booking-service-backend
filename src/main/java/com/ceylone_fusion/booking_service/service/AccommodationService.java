package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;

public interface AccommodationService {
    AccommodationDTO saveAccommodation(AccommodationSaveRequestDTO accommodationSaveRequestDTO);
}
