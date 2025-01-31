package com.ceylone_fusion.booking_service.util.mappers;

import com.ceylone_fusion.booking_service.dto.ExperienceCenterDTO;
import com.ceylone_fusion.booking_service.dto.response.ExperienceCenterGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.ExperienceCenter;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExperienceCenterMapper {

    List<ExperienceCenterDTO> ExperienceCenterEntityListToExperienceCenterDTOList(Page<ExperienceCenter> experienceCenters);

    List<ExperienceCenterGetResponseDTO> experienceCenterDTOListToExperienceCenterGetResponseDTOList(List<ExperienceCenterDTO> experienceCenterDTOS);

}
