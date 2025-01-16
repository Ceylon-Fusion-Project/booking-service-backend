package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExperienceCenterSaveRequestDTO {

    private String experienceCode;
    private String experienceName;
    private String experiencDescription;
    private String location;
    private Double totalPrice;
    private boolean isAvailable;

}
