package com.ceylone_fusion.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageRatingDTO {

    private Long ratingId;
    private Long customer;
    private Double packageRating;
    private String packageReview;
    private LocalDateTime ratedAt;
    private Long packageId;

}
