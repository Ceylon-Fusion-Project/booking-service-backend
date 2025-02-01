package com.ceylone_fusion.booking_service.dto.paginated;

import com.ceylone_fusion.booking_service.dto.response.PackageExperienceGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedPackageExperienceGetResponseDTO {

    private List<PackageExperienceGetResponseDTO> accommodationGetResponseDTOS;
    private Long total;

}
