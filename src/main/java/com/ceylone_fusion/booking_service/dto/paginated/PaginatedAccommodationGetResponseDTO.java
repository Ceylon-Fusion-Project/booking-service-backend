package com.ceylone_fusion.booking_service.dto.paginated;

import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedAccommodationGetResponseDTO {

    private List<AccommodationGetResponseDTO> accommodationGetResponseDTOS;
    private Long total;

}
