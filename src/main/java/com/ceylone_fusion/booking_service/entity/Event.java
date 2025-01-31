package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;

    @Column(name="event_name", nullable = false)
    private String eventName;

    @ElementCollection
    @CollectionTable(name = "event_images", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "image_urls")
    private List<String> eventImageURLs = new ArrayList<>();

    @Column(name="event_description")
    private String eventDescription;

    @Column(name="price_per_event", nullable = false)
    private Double pricePerEvent;

    @Column(name="is_available")
    private boolean isAvailable;

    @Column(name="start_time", nullable = false)
    private LocalTime startTime;

    @Column(name="end_time", nullable = false)
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name="experience_id", nullable=false)
    private ExperienceCenter experienceCenter;

    public Event(String eventName, List<String> eventImageURLs, String eventDescription, Double pricePerEvent, boolean available, LocalTime startTime, LocalTime endTime, ExperienceCenter experienceCenter) {
        this.eventName = eventName;
        this.eventImageURLs = eventImageURLs;
        this.eventDescription = eventDescription;
        this.pricePerEvent = pricePerEvent;
        this.isAvailable = available;
        this.startTime = startTime;
        this.endTime = endTime;
        this.experienceCenter = experienceCenter;
    }

}
