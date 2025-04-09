package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.BookingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingCartRepo extends JpaRepository<BookingCart, Long> {
    Optional<BookingCart> findByUserId(Long userId);
}

