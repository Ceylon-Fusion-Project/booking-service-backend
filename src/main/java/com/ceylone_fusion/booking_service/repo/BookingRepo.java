package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.Booking;
import com.ceylone_fusion.booking_service.entity.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface BookingRepo extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    List<Booking> findAllByBookingIdEquals(Long bookingId);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByStatusType(StatusType statusType);

    List<Booking> findByCheckInDate(LocalDateTime checkInDate);

    List<Booking> findByPackages_PackageId(Long packageId);

    List<Booking> findByCheckInDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
