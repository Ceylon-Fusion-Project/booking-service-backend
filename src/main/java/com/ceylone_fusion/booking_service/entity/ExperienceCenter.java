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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long experienceId;

    @Column(name="experience_code", nullable = false)
    private String experienceCode;

    @Column(name="name", nullable = false)
    private String experienceName;

    @Column(name="description")
    private String experienceDescription;

    @Column(name="location", nullable = false)
    private String location;

    @Column(name="total_price")
    private Double totalPrice;

    @Column(name="is_available")
    private boolean isAvailable;

    @OneToMany(mappedBy="experienceCenter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events;

}
