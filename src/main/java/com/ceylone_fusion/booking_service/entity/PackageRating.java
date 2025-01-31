package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "package_ratings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PackageRating {

    @Id
    @Column(name = "rating_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ratingId;

    @Column(name = "customer", nullable = false)
    private Long customer;

    @Column(name = "package_rating", nullable = false)
    private Double packageRating;

    @Column(name = "package_review")
    private String packageReview;

    @CreationTimestamp
    @Column(name = "rated_at", nullable = false, updatable = false)
    private LocalDateTime ratedAt;

    @ManyToOne
    @JoinColumn(name = "package_id", referencedColumnName = "package_id", nullable = false)
    private Package packages;

    public PackageRating(Long customer, Double packageRating, String packageReview, Package packages) {
        this.customer = customer;
        this.packageRating = packageRating;
        this.packageReview = packageReview;
        this.packages = packages;
    }
}
