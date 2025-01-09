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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accommodationId;

    @Column(name="name",nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="accommodation_type", nullable = false)
    private AccommodationType accommodationType;

    @Column(name="description")
    private String description;

    @Column(name="location", nullable = false)
    private String location;

    @OneToMany(mappedBy="accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

}
