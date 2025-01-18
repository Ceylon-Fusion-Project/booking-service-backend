package com.ceylone_fusion.booking_service.dto.paginated;

import com.ceylone_fusion.booking_service.dto.response.RoomGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginatedRoomGetResponseDTO {

    private List<RoomGetResponseDTO> roomGetResponseDTOS;
    private Long total;

}
