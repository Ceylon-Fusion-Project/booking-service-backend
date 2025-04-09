package com.ceylone_fusion.booking_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageRatingGetResponseDTO {

    private Long ratingId;
    private Long customer;
    private Double packageRating;
    private String packageReview;
    private LocalDateTime ratedAt;
    private Long packageId;

}
