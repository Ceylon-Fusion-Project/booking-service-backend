package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageAccommodationSaveRequestDTO {

    private int quantity;
    private Long packageId;
    private Long accommodationId;

}
