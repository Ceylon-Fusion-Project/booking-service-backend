package com.ceylone_fusion.booking_service.entity;

import com.ceylone_fusion.booking_service.entity.enums.AccommodationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "accommodations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Accommodation {

    @Id
    @Column(name = "accomodation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accommodationId;

    @Column(name = "accommodation_code", nullable = false, unique = true, updatable = false)
    private String accommodationCode;

    @Column(name="name",nullable = false)
    private String accommodationName;

    @Enumerated(EnumType.STRING)
    @Column(name="accommodation_type", nullable = false)
    private AccommodationType accommodationType;

    @Column(name="description")
    private String accommodationDescription;

    @Column(name="location", nullable = false)
    private String location;

    @Column(name = "is_available")
    private boolean isAvailable;

    @OneToMany(mappedBy="accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

}
