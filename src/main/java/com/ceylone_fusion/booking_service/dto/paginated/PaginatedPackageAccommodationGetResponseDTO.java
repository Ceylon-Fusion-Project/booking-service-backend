package com.ceylone_fusion.booking_service.dto.paginated;

import com.ceylone_fusion.booking_service.dto.response.PackageAccommodationGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedPackageAccommodationGetResponseDTO {

    private List<PackageAccommodationGetResponseDTO> accommodationGetResponseDTOS;
    private Long total;

}
