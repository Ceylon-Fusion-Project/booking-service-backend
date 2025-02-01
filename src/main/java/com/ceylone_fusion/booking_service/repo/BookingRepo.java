package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.Booking;
import com.ceylone_fusion.booking_service.entity.enums.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<Booking> findByStatusType(StatusType statusType);

    Page<Booking> findByStatusType(StatusType statusType, Pageable pageable);

    List<Booking> findByPackages_PackageId(Long packageId);

    Page<Booking> findByPackages_PackageId(Long packageId, Pageable pageable);

    List<Booking> findByCheckInDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    Page<Booking> findByCheckInDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Pageable pageable);

    List<Booking> findByCustomer(Long customer);

    Page<Booking> findByCustomer(Long customer, Pageable pageable);

}
