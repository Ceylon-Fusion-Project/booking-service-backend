package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "package_accommodations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageAccommodation {

    @Id
    @Column(name = "package_accommodation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long packageAccommodationId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name="package_id", nullable=false)
    private Package packages;

    @ManyToOne
    @JoinColumn(name="accommodation_id", nullable=false)
    private Accommodation accommodation;

}
