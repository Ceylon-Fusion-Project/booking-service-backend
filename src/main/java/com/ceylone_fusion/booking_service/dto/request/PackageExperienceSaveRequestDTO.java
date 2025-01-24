package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageExperienceSaveRequestDTO {

    private int quantity;
    private Long packageId;
    private Long experienceId;

}
