package com.ceylone_fusion.booking_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingCartItemResponseDTO {
    private Long bookingCartItemId;
    private Long bookingId;
    private Integer bookingItemQuantity;
    private Double bookingItemPrice;

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
}
