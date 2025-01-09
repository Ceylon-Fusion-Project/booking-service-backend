package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "experience_centers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExperienceCenter {

    @Id
    @Column(name = "experience_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long experienceId;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="location", nullable = false)
    private String location;

    @Column(name="total_price")
    private Double totalPrice;

    @OneToMany(mappedBy="experienceCenters", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events;

}
