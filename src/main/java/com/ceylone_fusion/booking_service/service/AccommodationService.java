package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedAccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.enums.AccommodationType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccommodationService {
    AccommodationDTO saveAccommodation(AccommodationSaveRequestDTO accommodationSaveRequestDTO);

    List<AccommodationGetResponseDTO> getAllAccommodations();

    List<AccommodationGetResponseDTO> getAccommodationById(Long accommodationId);

    List<AccommodationGetResponseDTO> getAccommodationByCode(String accommodationCode);

    AccommodationDTO updateAccommodationDetails(AccommodationUpdateRequestDTO accommodationUpdateRequestDTO, Long accommodationId);

    String deleteAccommodationById(Long accommodationId);

    PaginatedAccommodationGetResponseDTO getAllAccommodationsSorted(boolean isAvailable, Pageable pageable);

    PaginatedAccommodationGetResponseDTO getAccommodationByFiltering(String accommodationName, AccommodationType accommodationType, String location, boolean isAvailable, Pageable pageable);

    PaginatedAccommodationGetResponseDTO getAllAccommodationsPaginated(Pageable pageable);

}
