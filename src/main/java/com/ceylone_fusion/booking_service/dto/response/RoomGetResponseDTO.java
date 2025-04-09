package com.ceylone_fusion.booking_service.dto.response;

import com.ceylone_fusion.booking_service.entity.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomGetResponseDTO {

    private Long roomId;
    private String roomCode;
    private int roomNumber;
    private RoomType roomType;
    private List<String> roomImageURLs;
    private int beds;
    private Double pricePerNight;
    private boolean isAvailable;
    private Long accommodationId;

}
