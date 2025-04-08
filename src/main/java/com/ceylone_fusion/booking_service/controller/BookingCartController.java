package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.request.BookingCartRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.RemoveBookingCartItemRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.BookingCartResponseDTO;
import com.ceylone_fusion.booking_service.service.BookingCartService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking-cart")
public class BookingCartController {

    @Autowired
    private BookingCartService bookingCartService;

//    @PostMapping("/add-item-to-cart")
//    public ResponseEntity<StandardResponse> addToCart(@RequestBody BookingCartRequestDTO bookingCartRequestDTO) {
//        if (bookingCartRequestDTO == null || bookingCartRequestDTO.getUserId() == null || bookingCartRequestDTO.getBookingCartItem() == null) {
//            return ResponseEntity.badRequest().body(
//                    new StandardResponse(HttpStatus.BAD_REQUEST.value(), "Invalid request: User ID and booking item must be provided.", null)
//            );
//        }
//
//        try {
//            BookingCartResponseDTO bookingCartResponseDTO = bookingCartService.addToCart(bookingCartRequestDTO);
//            return ResponseEntity.ok(
//                    new StandardResponse(HttpStatus.OK.value(), "Item added to booking cart successfully.", bookingCartResponseDTO)
//            );
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(
//                    new StandardResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null)
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                    new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add item to booking cart.", null)
//            );
//        }
//    }

//    @DeleteMapping("/remove-item-from-cart")
//    public ResponseEntity<StandardResponse> removeCartItem(@RequestBody RemoveBookingCartItemRequestDTO removeBookingCartItemRequestDTO) {
//        if (removeBookingCartItemRequestDTO == null || removeBookingCartItemRequestDTO.getUserId() == null || removeBookingCartItemRequestDTO.getBookingId() == null) {
//            return ResponseEntity.badRequest().body(
//                    new StandardResponse(HttpStatus.BAD_REQUEST.value(), "Invalid request: User ID and booking ID must be provided.", null)
//            );
//        }
//
//        try {
//            BookingCartResponseDTO updatedCart = bookingCartService.removeCartItem(removeBookingCartItemRequestDTO);
//            return ResponseEntity.ok(
//                    new StandardResponse(HttpStatus.OK.value(), "Item removed from booking cart successfully.", updatedCart)
//            );
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(
//                    new StandardResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null)
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                    new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to remove item from booking cart.", null)
//            );
//        }
//    }

//    @GetMapping(path = "/get-cart-items/user", params = "userId")
//    public ResponseEntity<StandardResponse> getCartItemsByUserId(@RequestParam("userId") Long userId) {
//        if (userId == null) {
//            return ResponseEntity.badRequest().body(
//                    new StandardResponse(HttpStatus.BAD_REQUEST.value(), "Invalid request: User ID must be provided.", null)
//            );
//        }
//
//        try {
//            BookingCartResponseDTO responseDto = bookingCartService.getCartItemsByUserId(userId);
//            return ResponseEntity.ok(
//                    new StandardResponse(HttpStatus.OK.value(), "Booking cart items fetched successfully.", responseDto)
//            );
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(
//                    new StandardResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null)
//            );
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                    new StandardResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to fetch booking cart items.", null)
//            );
//        }
//    }
}
