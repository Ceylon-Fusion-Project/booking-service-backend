package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingRating {

    @Id
    @Column(name = "rating_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "review")
    private String review;

    @CreationTimestamp
    @Column(name = "rated_at", nullable = false, updatable = false)
    private LocalDateTime ratedAt;

    @ManyToOne
    @JoinColumn(name = "package_id", referencedColumnName = "package_id", nullable = false)
    private Package packages;

}
