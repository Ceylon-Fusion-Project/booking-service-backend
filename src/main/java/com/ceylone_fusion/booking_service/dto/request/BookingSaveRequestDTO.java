package com.ceylone_fusion.booking_service.dto.request;

import com.ceylone_fusion.booking_service.entity.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingSaveRequestDTO {

    private Long customer;
    private StatusType statusType;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Double totalCost;
    private Long packageId;

}
