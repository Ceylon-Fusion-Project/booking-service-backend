package com.ceylone_fusion.booking_service.service;


import com.ceylone_fusion.booking_service.dto.ExperienceCenterDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedExperienceCenterGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.ExperienceCenterGetResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExperienceCenterService {

    ExperienceCenterDTO saveExperienceCenter(ExperienceCenterSaveRequestDTO experienceCenterSaveRequestDTO);

    List<ExperienceCenterGetResponseDTO> getAllExperienceCenters();

    List<ExperienceCenterGetResponseDTO> getExperienceCenterById(Long experienceId);

    List<ExperienceCenterGetResponseDTO> getExperienceCenterByCode(String experienceCode);

    ExperienceCenterDTO updateExperienceCenterDetails(ExperienceCenterUpdateRequestDTO experienceCenterUpdateRequestDTO, Long experienceId);

    String deleteExperienceCenterById(Long experienceId);

    PaginatedExperienceCenterGetResponseDTO getAllExperienceCentersSorted(boolean isAvailable, Pageable pageable);

    PaginatedExperienceCenterGetResponseDTO getExperienceCenterByFiltering(String experienceName, String location, boolean isAvailable, Pageable pageable);
}
