package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageRatingSaveRequestDTO {

    private Long customer;
    private Double packageRating;
    private String packageReview;
    private LocalDateTime ratedAt;
    private Long packageId;

}
