package com.ceylone_fusion.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageDTO {

    private Long packageId;
    private String packageName;
    private String packageCode;
    private String description;
    private Double pricePerDay;
    private boolean isPredefined;
    private Double packageRatingValue;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    
}
