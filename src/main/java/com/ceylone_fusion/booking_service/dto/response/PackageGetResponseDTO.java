package com.ceylone_fusion.booking_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageGetResponseDTO {

    private String packageName;
    private String description;
    private Double price;
    private boolean isPredefined;
    private Double packageRatingValue;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<Long> roomIds;
    private List<Long> eventIds;

}
