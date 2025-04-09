package com.ceylone_fusion.booking_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingCartItemRequestDTO {

    private Long bookingId; // e.g., accommodation, experience, or package ID
    private Integer bookingItemQuantity;
    private Double bookingItemPrice;

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setBookingItemQuantity(Integer bookingItemQuantity) {
        this.bookingItemQuantity = bookingItemQuantity;
    }

    public void setBookingItemPrice(Double bookingItemPrice) {
        this.bookingItemPrice = bookingItemPrice;
    }

}
