package com.ceylone_fusion.booking_service.entity;

import com.ceylone_fusion.booking_service.entity.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Room {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;

    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "room_number")
    private int roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="room_type", nullable = false)
    private RoomType roomType;

    @ElementCollection
    @CollectionTable(name = "room_images", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "image_urls")
    private List<String> roomImageURLs = new ArrayList<>();

    @Column(name = "beds")
    private int beds;

    @Column(name="price_per_night", nullable = false)
    private Double pricePerNight;

    @Column(name = "is_available",nullable = false, columnDefinition = "boolean default true")
    private boolean isAvailable = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="accommodation_id", nullable=false)
    private Accommodation accommodation;

    public Room(String roomCode, int roomNumber, RoomType roomType, List<String> roomImageURLs, int beds, Double pricePerNight, boolean isAvailable, Accommodation accommodation){
        this.roomCode = roomCode;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomImageURLs = roomImageURLs;
        this.beds = beds;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
        this.accommodation = accommodation;
    }

}
