package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.BookingDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedBookingGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.BookingSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.BookingUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.BookingGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.enums.StatusType;
import com.ceylone_fusion.booking_service.service.BookingService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
@CrossOrigin
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(path = "/save-booking")
    public ResponseEntity<StandardResponse> createBooking(@RequestBody BookingSaveRequestDTO bookingSaveRequestDTO) {
        try {
            BookingDTO response = bookingService.saveBooking(bookingSaveRequestDTO);

            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(201, "Booking Saved Successfully", response.getBookingId()),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(409, e.getMessage(), null),
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping(path = "/get-all-bookings")
    public ResponseEntity<StandardResponse> getAllBookings() {
        try {
            List<BookingGetResponseDTO> allBookings = bookingService.getAllBookings();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Success", allBookings),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-all-bookings-paginated")
    public ResponseEntity<StandardResponse> getAllBookings(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        try {
            // Pagination Specification
            PageRequest pageRequest = PageRequest.of(page, size);
            PaginatedBookingGetResponseDTO response = bookingService.getAllBookingsPaginated(pageRequest);
            return new ResponseEntity<>(
                    new StandardResponse(200, "Bookings Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(
            path = "/get-booking-details-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> getBookingById(@RequestParam(value = "id") Long bookingId) {
        try {
            List<BookingGetResponseDTO> response = bookingService.getBookingById(bookingId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Booking Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-booking-details")
    public ResponseEntity<StandardResponse> getAllBookingDetails(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "statusType", required = false) StatusType statusType,
            @RequestParam(value = "checkInDate", required = false) LocalDate checkInDate,
            @RequestParam(value = "packageId", required = false) Long packageId
    ){
        try {
            // Fetch booking details using the service
            List<BookingGetResponseDTO> response =
                    bookingService.getAllBookingDetails(userId, statusType, checkInDate, packageId);
            // Return successful response
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, " Booking Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-booking-details-paginated")
    public ResponseEntity<StandardResponse> getAllBookingDetails(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "statusType", required = false) StatusType statusType,
            @RequestParam(value = "checkInDate", required = false) LocalDate checkInDate,
            @RequestParam(value = "packageId", required = false) Long packageId,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ){
        try {
            // Pagination Specification
            PageRequest pageRequest = PageRequest.of(page, size);
            PaginatedBookingGetResponseDTO response = bookingService.getAllBookingDetailsPaginated(userId, statusType, checkInDate, packageId, pageRequest);
            // Return successful response
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Booking Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PatchMapping(
            path = "/update-booking-details",
            params = "id"
    )
    public ResponseEntity<StandardResponse> updateBookingDetails(
            @RequestBody BookingUpdateRequestDTO bookingUpdateRequestDTO,
            @RequestParam(value = "id") Long bookingId
    ) {
        try {
            BookingDTO response = bookingService.updateBookingDetails(bookingUpdateRequestDTO, bookingId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Package Updated Successfully", response.getBookingId()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping(
            path = "/delete-booking-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> deleteBookingById(@RequestParam(value = "id") Long bookingId) {
        try {
            String response = bookingService.deleteBookingById(bookingId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, response, "Booking Deleted Successfully"),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

}
