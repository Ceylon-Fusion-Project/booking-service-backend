package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.BookingDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedBookingGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.BookingSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.BookingUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.BookingGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.enums.StatusType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    BookingDTO saveBooking(BookingSaveRequestDTO bookingSaveRequestDTO);

    List<BookingGetResponseDTO> getAllBookings();

    List<BookingGetResponseDTO> getBookingById(Long bookingId);

    List<BookingGetResponseDTO> getAllBookingDetails(Long userId, StatusType statusType, LocalDate checkInDate, Long packageId);

    BookingDTO updateBookingDetails(BookingUpdateRequestDTO bookingUpdateRequestDTO, Long bookingId);

    String deleteBookingById(Long bookingId);

    PaginatedBookingGetResponseDTO getAllBookingsPaginated(Pageable pageable);

}
