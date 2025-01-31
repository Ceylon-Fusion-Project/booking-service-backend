package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;



@Entity
@Table(name = "packages")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Package {

    @Id
    @Column(name = "package_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long packageId;

    @Column(name="package_name", nullable = false)
    private String packageName;

    @Column(name="description")
    private String description;

    @Column(name="price", nullable = false)
    private Double pricePerDay;

    @Column(name = "is_predefined")
    private boolean isPredefined;

    @Column(name = "package_avg_rating")
    private Double packageRatingValue = 0.0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    @OneToMany(mappedBy="packages", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackageAccommodation> packageAccommodations;

    @OneToMany(mappedBy="packages", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackageExperience> packageExperiences;

    @OneToOne(mappedBy="packages", cascade = CascadeType.ALL, orphanRemoval = true)
    private Booking booking;

    @OneToMany(mappedBy="packages", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PackageRating> packageRatings;

}
