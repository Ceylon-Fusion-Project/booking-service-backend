package com.ceylone_fusion.booking_service.dto;

import com.ceylone_fusion.booking_service.entity.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingDTO {

    private Long bookingId;
    private Long userId;
    private StatusType statusType;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Double totalCost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long packageId;

}
