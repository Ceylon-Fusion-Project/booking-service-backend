package com.ceylone_fusion.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDTO {

    private Long eventId;
    private String eventName;
    private List<String> eventImageURLs;
    private String eventDescription;
    private Double pricePerEvent;
    private boolean isAvailable;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long experienceId;

}
