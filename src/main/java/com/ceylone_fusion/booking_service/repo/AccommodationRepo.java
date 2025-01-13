package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface AccommodationRepo extends JpaRepository<Accommodation, Long>, JpaSpecificationExecutor<Accommodation> {
    boolean existsByAccommodationCodeEqualsIgnoreCase(String accommodationCode);

    List<Accommodation> findAllByAccommodationIdEquals(Long accommodationId);

    List<Accommodation> findAllByAccommodationCodeEquals(String accommodationCode);

    Accommodation findAccommodationByAccommodationIdEquals(Long accommodationId);

    List<Accommodation> findRoomsByAccommodationIdEquals(Long accommodationId);
}
