package com.ceylone_fusion.booking_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingCartResponseDTO {

    private Long cartId;
    private Long userId;
    private List<BookingCartItemResponseDTO> bookingCartItems;

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

    public List<BookingCartItemResponseDTO> getBookingCartItems() {
        return bookingCartItems;
    }

    public void setBookingCartItems(List<BookingCartItemResponseDTO> bookingCartItems) {
        this.bookingCartItems = bookingCartItems;
    }
}
