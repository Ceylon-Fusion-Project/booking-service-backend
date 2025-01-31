package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.Accommodation;
import com.ceylone_fusion.booking_service.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface RoomRepo extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    List<Room> findAllRoomsByRoomIdEquals(Long roomId);

    boolean existsByAccommodation_AccommodationId(Long accommodationId);

    List<Room> findRoomByAccommodationIn(List<Accommodation> accommodations);

    Page<Room> findAllByIsAvailableEquals(boolean isAvailable, Pageable pageable);

    Long countRoomByIsAvailableEquals(boolean isAvailable);

    @Query("SELECT r FROM Room r WHERE r.accommodation.accommodationId = :accommodationId")
    Page<Room> findByAccommodationId(Long accommodationId, Pageable pageable);

}
