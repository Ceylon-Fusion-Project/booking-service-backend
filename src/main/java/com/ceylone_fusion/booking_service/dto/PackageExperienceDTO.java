package com.ceylone_fusion.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageExperienceDTO {

    private Long packageExperienceId;
    private int quantity;
    private Long packageId;
    private Long experienceId;

}
