package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingCartRequestDTO {

    private Long userId;
    private BookingCartItemRequestDTO bookingCartItem;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BookingCartItemRequestDTO getBookingCartItem() {
        return bookingCartItem;
    }

    public void setBookingCartItem(BookingCartItemRequestDTO bookingCartItem) {
        this.bookingCartItem = bookingCartItem;
    }
}
