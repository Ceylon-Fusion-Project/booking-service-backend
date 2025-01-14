package com.ceylone_fusion.booking_service.dto.response;

import com.ceylone_fusion.booking_service.entity.enums.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccommodationGetResponseDTO {

    private Long accommodationId;
    private String accommodationCode;
    private String accommodationName;
    private AccommodationType accommodationType;
    private String description;
    private String location;
    private boolean isAvailable;

}
