package com.ceylone_fusion.booking_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "bookingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingCartItem> bookingCartItems;  // List of booked items (accommodations, experiences, packages)

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<BookingCartItem> getBookingCartItems() {
        return bookingCartItems;
    }

    public void setBookingCartItems(List<BookingCartItem> bookingCartItems) {
        this.bookingCartItems = bookingCartItems;
    }

    // Convenience methods for managing bidirectional relationships
    public void addBookingCartItem(BookingCartItem bookingCartItem) {
        bookingCartItems.add(bookingCartItem);
        bookingCartItem.setBookingCart(this);
    }

    public void removeBookingCartItem(BookingCartItem bookingCartItem) {
        bookingCartItems.remove(bookingCartItem);
        bookingCartItem.setBookingCart(null);
    }

}
