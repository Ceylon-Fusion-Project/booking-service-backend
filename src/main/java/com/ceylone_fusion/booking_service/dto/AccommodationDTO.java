package com.ceylone_fusion.booking_service.dto;

import com.ceylone_fusion.booking_service.entity.enums.AccommodationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccommodationDTO {

    private Long accommodationId;
    private String accommodationCode;
    private String accommodationName;
    private AccommodationType accommodationType;
    private String description;
    private String location;

}
