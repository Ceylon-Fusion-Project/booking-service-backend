package com.ceylone_fusion.booking_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExperienceCenterGetResponseDTO {

    private String experienceCode;
    private String experienceName;
    private String experienceDescription;
    private String location;
    private Double totalPrice;
    private boolean isAvailable;

}
