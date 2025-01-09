package com.ceylone_fusion.booking_service.entity;

import com.ceylone_fusion.booking_service.entity.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Room {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(name = "room_number")
    private int roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "beds")
    private int beds;

    @Column(name="price_per_night", nullable = false)
    private Double pricePerNight;

    @Column(name = "is_available")
    private boolean isAvailable;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="accommodation_id", nullable=false)
    private Accommodation accommodation;

}
