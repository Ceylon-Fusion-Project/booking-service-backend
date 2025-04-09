package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageUpdateRequestDTO {

    private String packageName;
    private String description;
    private Double pricePerDay;
    private boolean isPredefined;
    private List<Long> roomIds;
    private List<Long> eventIds;

}
