package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(name="event_name", nullable = false)
    private String eventName;

    @Column(name="event_description")
    private String eventDescription;

    @Column(name="price_per_event", nullable = false)
    private Double pricePerEvent;

    @Column(name="start_time", nullable = false)
    private LocalTime startTime;

    @Column(name="end_time", nullable = false)
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name="experience_id", nullable=false)
    private ExperienceCenter experienceCenters;

}
