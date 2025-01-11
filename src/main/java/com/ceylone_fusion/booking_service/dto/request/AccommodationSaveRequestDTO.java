package com.ceylone_fusion.booking_service.dto.request;

import com.ceylone_fusion.booking_service.entity.Room;
import com.ceylone_fusion.booking_service.entity.enums.AccommodationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccommodationSaveRequestDTO {

    private String accommodationCode;
    private String accommodationName;
    private AccommodationType accommodationType;
    private String accommodationDescription;
    private String location;

}
