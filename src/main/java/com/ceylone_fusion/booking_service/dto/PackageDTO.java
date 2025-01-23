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
    private String description;
    private Double price;
    private boolean isPredefined;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    
}
