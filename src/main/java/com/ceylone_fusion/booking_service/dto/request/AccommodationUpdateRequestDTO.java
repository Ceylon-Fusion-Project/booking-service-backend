package com.ceylone_fusion.booking_service.dto.request;

import com.ceylone_fusion.booking_service.entity.enums.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccommodationUpdateRequestDTO {

    private String accommodationName;
    private AccommodationType accommodationType;
    private String accommodationDescription;
    private String location;
    private boolean isAvailable;

}
