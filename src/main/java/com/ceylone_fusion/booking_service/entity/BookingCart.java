package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking_cart")
public class BookingCart{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private Long cartId;
//
//    @Column(name = "user_id", nullable = false)
//    private Long userId;  // User who is making the booking
//
//    @OneToMany(mappedBy = "bookingCart", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BookingItem> bookingItems;  // List of booked items (accommodations, experiences, packages)
//
//    public Long getCartId() {
//        return cartId;
//    }
//
//    public void setCartId(Long cartId) {
//        this.cartId = cartId;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public List<BookingItem> getBookingItems() {
//        return bookingItems;
//    }
//
//    public void setBookingItems(List<BookingItem> bookingItems) {
//        this.bookingItems = bookingItems;
//    }
//
//    // Convenience methods for managing bidirectional relationships
//    public void addBookingItem(BookingItem bookingItem) {
//        bookingItems.add(bookingItem);
//        bookingItem.setBookingCart(this);
//    }
//
//    public void removeBookingItem(BookingItem bookingItem) {
//        bookingItems.remove(bookingItem);
//        bookingItem.setBookingCart(null);
//    }

}
