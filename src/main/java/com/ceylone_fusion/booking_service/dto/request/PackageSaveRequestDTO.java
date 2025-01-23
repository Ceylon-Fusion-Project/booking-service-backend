package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageSaveRequestDTO {

    private String packageName;
    private String description;
    private Double price;
    private boolean isPredefined;

}
