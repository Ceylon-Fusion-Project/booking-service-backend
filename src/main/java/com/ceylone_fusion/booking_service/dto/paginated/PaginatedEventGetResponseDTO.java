package com.ceylone_fusion.booking_service.dto.paginated;

import com.ceylone_fusion.booking_service.dto.response.EventGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedEventGetResponseDTO {

    private List<EventGetResponseDTO> eventGetResponseDTOS;
    private Long total;

}
