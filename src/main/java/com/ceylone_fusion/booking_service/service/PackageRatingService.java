package com.ceylone_fusion.booking_service.service;


import com.ceylone_fusion.booking_service.dto.PackageRatingDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedPackageRatingGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageRatingSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageRatingUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageRatingGetResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PackageRatingService {

    String savePackageRating(PackageRatingSaveRequestDTO packageRatingSaveRequestDTO);

    List<PackageRatingGetResponseDTO> getPackageRatingById(Long ratingId);

    PaginatedPackageRatingGetResponseDTO getPackageRatingByPackageId(Long packageId, Pageable pageable);

    PaginatedPackageRatingGetResponseDTO getPackageRatingByCustomerId(Long customerId, Pageable pageable);

    PackageRatingDTO updatePackageRating(PackageRatingUpdateRequestDTO packageRatingUpdateRequestDTO, Long packageRatingId);

    String deletePackageRatingById(Long packageRatingId);
}
