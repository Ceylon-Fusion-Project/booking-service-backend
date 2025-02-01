package com.ceylone_fusion.booking_service.dto.paginated;

import com.ceylone_fusion.booking_service.dto.response.PackageGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedPackageGetResponseDTO {

    private List<PackageGetResponseDTO> packageRatingGetResponseDTOS;
    private Long total;

}
