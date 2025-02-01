package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.PackageAccommodationDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedPackageAccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageAccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageAccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageAccommodationGetResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PackageAccommodationService {

    PackageAccommodationDTO savePackageAccommodation(PackageAccommodationSaveRequestDTO packageAccommodationSaveRequestDTO);

    List<PackageAccommodationSaveRequestDTO> getAllPackageAccommodations();

    List<PackageAccommodationGetResponseDTO> getPackageAccommodationById(Long packageAccommodationId);

    List<PackageAccommodationGetResponseDTO> getAllPackageAccommodationDetails(Long packageId, Long accommodationId);

    PackageAccommodationDTO updatePackageAccommodationDetails(PackageAccommodationUpdateRequestDTO packageAccommodationUpdateRequestDTO, Long packageAccommodationId);

    String deletePackageAccommodationById(Long packageAccommodationId);

    PaginatedPackageAccommodationGetResponseDTO getAllPackageAccommodationsPaginated(Pageable pageable);

    PaginatedPackageAccommodationGetResponseDTO getAllPackageAccommodationDetailsPaginated(Long packageId, Long accommodationId, Pageable pageable);

}
