package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventUpdateRequestDTO {

    private String eventName;
    private List<String> eventImageURLs;
    private String eventDescription;
    private Double pricePerEvent;
    private boolean isAvailable;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
