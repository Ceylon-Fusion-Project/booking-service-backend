package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.BookingDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedBookingGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.BookingSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.BookingUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.BookingGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Booking;
import com.ceylone_fusion.booking_service.entity.Package;
import com.ceylone_fusion.booking_service.entity.enums.StatusType;
import com.ceylone_fusion.booking_service.repo.BookingRepo;
import com.ceylone_fusion.booking_service.repo.PackageRepo;
import com.ceylone_fusion.booking_service.service.BookingService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceIMPL implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PackageRepo packageRepo;

    @Override
    public BookingDTO saveBooking(BookingSaveRequestDTO bookingSaveRequestDTO) {
        Long customer = bookingSaveRequestDTO.getCustomer();
        Long packageId = bookingSaveRequestDTO.getPackageId();
        if (bookingRepo.existsById(customer)) {
            // Retrieve the package details
            Package packageDetails = packageRepo.findPackagesByPackageIdEquals(packageId);
            if (!(packageDetails == null)) {
                // Calculate total cost based on the booking duration and package price
                LocalDateTime checkInDate = bookingSaveRequestDTO.getCheckInDate();
                LocalDateTime checkOutDate = bookingSaveRequestDTO.getCheckOutDate();
                long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                if (numberOfDays >= 0) {
                    double totalCost = numberOfDays * packageDetails.getPricePerDay();
                    // Create and save the new booking
                    Booking newBooking = new Booking(
                            customer,
                            bookingSaveRequestDTO.getStatusType(),
                            checkInDate,
                            checkOutDate,
                            totalCost,
                            packageDetails
                    );

                    bookingRepo.save(newBooking);
                    // Convert to DTO and return
                    return modelMapper.map(newBooking, BookingDTO.class);
                } else {
                    throw new RuntimeException("Invalid Check-In and Check-Out Dates");
                }
            } else{
                throw new RuntimeException("Package not found");
            }
        } else {
            throw new RuntimeException("Customer Not Found");
        }
    }

    @Override
    public List<BookingGetResponseDTO> getAllBookings() {
        List<Booking> getAllBookings = bookingRepo.findAll();
        if(!getAllBookings.isEmpty()){
            return modelMapper.map(getAllBookings, new TypeToken<List<BookingGetResponseDTO>>(){}.getType());
        }
        else{
            throw new RuntimeException("No Bookings Found");
        }
    }

    @Override
    public PaginatedBookingGetResponseDTO getAllBookingsPaginated(Pageable pageable) {
        // Fetch paginated bookings
        Page<Booking> bookingsPage = bookingRepo.findAll(pageable);
        if (bookingsPage.hasContent()) {
            // Convert Page<Booking> to List<BookingGetResponseDTO>
            List<BookingGetResponseDTO> bookingGetResponseDTOS = bookingsPage.getContent().stream()
                    .map(booking -> modelMapper.map(booking, BookingGetResponseDTO.class))
                    .collect(Collectors.toList());
            // Return paginated response
            return new PaginatedBookingGetResponseDTO(
                    bookingGetResponseDTOS,
                    bookingsPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Bookings Found");
        }
    }

    @Override
    public List<BookingGetResponseDTO> getBookingById(Long bookingId) {
        List<Booking> bookings = bookingRepo.findAllByBookingIdEquals(bookingId);
        if(!bookings.isEmpty()) {
            return modelMapper.map(bookings, new TypeToken<List<BookingGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Booking Found");
        }
    }

    @Override
    public List<BookingGetResponseDTO> getAllBookingDetails(
            Long customer,
            StatusType statusType,
            LocalDate checkInDate,
            Long packageId
    ) {
        List<Booking> bookings;
        // Apply filters based on input parameters
        if (customer != null) {
            bookings = bookingRepo.findByCustomer(customer);
        } else if (statusType != null) {
            bookings = bookingRepo.findByStatusType(statusType);
        } else if (checkInDate != null) {
            // Convert LocalDate to LocalDateTime at midnight (start of the day) for filtering
            LocalDateTime startOfDay = checkInDate.atStartOfDay();
            LocalDateTime endOfDay = checkInDate.atTime(23, 59, 59);  // End of the day
            // Find bookings that fall within the start and end of the given date
            bookings = bookingRepo.findByCheckInDateBetween(startOfDay, endOfDay);
        } else if (packageId != null) {
            bookings = bookingRepo.findByPackages_PackageId(packageId);
        } else {
            bookings = bookingRepo.findAll(); // Retrieve all bookings if no filters are applied
        }
        // Handle empty or null results
        if (bookings == null || bookings.isEmpty()) {
            throw new RuntimeException("No bookings found matching the criteria.");
        }
        // Map Package entities to DTOs
        return modelMapper.map(bookings, new TypeToken<List<BookingGetResponseDTO>>() {}.getType());
    }

    @Override
    public PaginatedBookingGetResponseDTO getAllBookingDetailsPaginated(Long customer, StatusType statusType, LocalDate checkInDate, Long packageId, Pageable pageable) {
        Page<Booking> bookingsPage;
        if (customer != null) {
            bookingsPage = bookingRepo.findByCustomer(customer, pageable);
        } else if (statusType != null) {
            bookingsPage = bookingRepo.findByStatusType(statusType, pageable);
        } else if (checkInDate != null) {
            // Convert LocalDate to LocalDateTime at midnight (start of the day) for filtering
            LocalDateTime startOfDay = checkInDate.atStartOfDay();
            LocalDateTime endOfDay = checkInDate.atTime(23, 59, 59);  // End of the day
            // Find bookings that fall within the start and end of the given date
            bookingsPage = bookingRepo.findByCheckInDateBetween(startOfDay, endOfDay, pageable);
        } else if (packageId != null) {
            bookingsPage = bookingRepo.findByPackages_PackageId(packageId, pageable);
        } else {
            bookingsPage = bookingRepo.findAll(pageable); // Retrieve all bookings if no filters are applied
        }
        if (bookingsPage.hasContent()) {
            List<BookingGetResponseDTO> bookingGetResponseDTOS = bookingsPage.getContent().stream()
                    .map(booking -> modelMapper.map(booking, BookingGetResponseDTO.class))
                    .toList();
            return new PaginatedBookingGetResponseDTO(
                    bookingGetResponseDTOS,
                    bookingsPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Packages Found");
        }
    }

    @Override
    public BookingDTO updateBookingDetails(BookingUpdateRequestDTO bookingUpdateRequestDTO, Long bookingId) {
        //Get booking by Booking ID
        if (bookingRepo.existsById(bookingId)) {
            // Get Booking by Booking ID and Map Booking Entity to Booking DTO
            Booking existingBooking = bookingRepo.getReferenceById(bookingId);
            // Update Booking name
            if (bookingUpdateRequestDTO.getStatusType() != null) {
                existingBooking.setStatusType(bookingUpdateRequestDTO.getStatusType());
            }
            // Update Booking Check In Date
            if (bookingUpdateRequestDTO.getCheckInDate() != null) {
                existingBooking.setCheckInDate(bookingUpdateRequestDTO.getCheckInDate());
            }
            // Update Booking Check Out Date
            if (bookingUpdateRequestDTO.getCheckOutDate() != null) {
                existingBooking.setCheckOutDate(bookingUpdateRequestDTO.getCheckOutDate());
            }
            // Retrieve package price and calculate total cost
            if (existingBooking.getPackages().getPackageId() != null &&
                    bookingUpdateRequestDTO.getCheckInDate() != null &&
                    bookingUpdateRequestDTO.getCheckOutDate() != null
            ) {
                Long packageId = existingBooking.getPackages().getPackageId();
                // Retrieve package details (assume a PackageRepository exists)
                Package packageDetails = packageRepo.findById(packageId)
                        .orElseThrow(() -> new RuntimeException("Package not found"));
                // Calculate the number of days
                long numberOfDays = ChronoUnit.DAYS.between(
                        bookingUpdateRequestDTO.getCheckInDate(),
                        bookingUpdateRequestDTO.getCheckOutDate()
                );
                if (numberOfDays <= 0) {
                    throw new RuntimeException("Check-out date must be after check-in date.");
                }
                // Calculate total cost
                double totalCost = numberOfDays * packageDetails.getPricePerDay();
                // Update total cost
                existingBooking.setTotalCost(totalCost);
            }
            // Save the updated Booking
            bookingRepo.save(existingBooking);
            return modelMapper.map(existingBooking, BookingDTO.class);
        } else {
            throw new RuntimeException("Booking Not Found");
        }
    }

    @Override
    public String deleteBookingById(Long bookingId) {
        // Get Booking by Booking ID
        if (bookingRepo.existsById(bookingId)) {
            String response = bookingRepo.getReferenceById(bookingId).getBookingId() + " Deleted!";
            //delete Booking
            bookingRepo.deleteById(bookingId);
            return response;
        } else {
            throw new RuntimeException("Booking Not Found");
        }
    }

}
