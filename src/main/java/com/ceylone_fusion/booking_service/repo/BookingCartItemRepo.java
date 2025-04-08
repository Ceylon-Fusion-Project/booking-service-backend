package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.BookingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingCartItemRepo extends JpaRepository<BookingCartItem, Long> {
}
