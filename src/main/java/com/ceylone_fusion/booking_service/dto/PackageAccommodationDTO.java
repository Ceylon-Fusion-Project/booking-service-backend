package com.ceylone_fusion.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageAccommodationDTO {

    private Long PackageAccommodationId;
    private int quantity;
    private Long packageId;
    private Long accommodationId;

}
