package com.ceylone_fusion.booking_service.dto.paginated;

import com.ceylone_fusion.booking_service.dto.response.PackageRatingGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedPackageRatingGetResponseDTO {

    private List<PackageRatingGetResponseDTO> packageRatingGetResponseDTOS;
    private Long total;

}
