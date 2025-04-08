package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking_cart_item")
public class BookingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_cart_item_id", nullable = false)
    private Long bookingCartItemId;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;  // The ID of the booking item (e.g., accommodation, experience, or package)

    @Column(name = "booking_item_quantity", nullable = false)
    private Integer bookingItemQuantity;  // Number of units for this booking (e.g., number of rooms, number of tickets, etc.)

    @Column(name = "booking_item_price", nullable = false)
    private Double bookingItemPrice;  // Price per unit for the booking item (e.g., price per room, ticket, etc.)

    @ManyToOne
    @JoinColumn(name = "booking_cart_id", nullable = false)
    private BookingCart bookingCart;  // Reference to the booking cart

    public Long getBookingCartItemId() {
        return bookingCartItemId;
    }

    public void setBookingCartItemId(Long bookingCartItemId) {
        this.bookingCartItemId = bookingCartItemId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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

    public BookingCart getBookingCart() {
        return bookingCart;
    }

    public void setBookingCart(BookingCart bookingCart) {
        this.bookingCart = bookingCart;
    }

}
