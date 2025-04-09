package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExperienceCenterUpdateRequestDTO {

    private String experienceName;
    private String experienceDescription;
    private String location;
    private String expCenterMapLink;
    private String expDemoVideoLink;
    private Double totalPrice;
    private boolean isAvailable;

}
