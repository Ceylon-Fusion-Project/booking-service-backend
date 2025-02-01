package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.PackageExperienceDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedPackageExperienceGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageExperienceSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageExperienceUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageExperienceGetResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PackageExperienceService {

    PackageExperienceDTO savePackageExperience(PackageExperienceSaveRequestDTO packageAccommodationSaveRequestDTO);

    List<PackageExperienceSaveRequestDTO> getAllPackageExperiences();

    List<PackageExperienceGetResponseDTO> getPackageExperienceById(Long packageExperienceId);

    List<PackageExperienceGetResponseDTO> getAllPackageExperienceDetails(Long packageId, Long experienceId);

    PackageExperienceDTO updatePackageExperienceDetails(PackageExperienceUpdateRequestDTO packageExperienceUpdateRequestDTO, Long packageExperienceId);

    String deletePackageExperienceById(Long packageExperienceId);

    PaginatedPackageExperienceGetResponseDTO getAllPackageExperiencesPaginated(Pageable pageable);
}
