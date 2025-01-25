package com.ceylone_fusion.booking_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageGetResponseDTO {

    private String packageName;
    private String description;
    private Double price;
    private boolean isPredefined;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
