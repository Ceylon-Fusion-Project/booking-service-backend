package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking_items")
public class BookingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_item_id")
    private Long bookingItemId;

    @Column(name = "booking_item_type", nullable = false)
    private String bookingItemType;  // Type of item, e.g., "Accommodation", "Experience", "Package"

    @Column(name = "item_id", nullable = false)
    private Long itemId;  // The ID of the booked item (accommodation, event, or package)

    @Column(name = "booking_item_quantity", nullable = false)
    private Integer bookingItemQuantity;  // Number of items booked

    @Column(name = "booking_item_price", nullable = false)
    private Double bookingItemPrice;  // Price of the item at the time of booking

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;  // The booking to which this item belongs

    public Long getBookingItemId() {
        return bookingItemId;
    }

    public void setBookingItemId(Long bookingItemId) {
        this.bookingItemId = bookingItemId;
    }

    public String getBookingItemType() {
        return bookingItemType;
    }

    public void setBookingItemType(String bookingItemType) {
        this.bookingItemType = bookingItemType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getBookingItemQuantity() {
        return bookingItemQuantity;
    }

    public void setBookingItemQuantity(Integer bookingItemQuantity) {
        this.bookingItemQuantity = bookingItemQuantity;
    }

    public Double getBookingItemPrice() {
        return bookingItemPrice;
    }

    public void setBookingItemPrice(Double bookingItemPrice) {
        this.bookingItemPrice = bookingItemPrice;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

}
