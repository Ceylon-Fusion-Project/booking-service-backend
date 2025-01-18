package com.ceylone_fusion.booking_service.dto.paginated;

import com.ceylone_fusion.booking_service.dto.response.ExperienceCenterGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedExperienceCenterGetResponseDTO {

    private List<ExperienceCenterGetResponseDTO> experienceCenterGetResponseDTOS;
    private Long total;

}
