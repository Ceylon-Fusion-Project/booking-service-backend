package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.PackageAccommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PackageAccommodationRepo extends JpaRepository<PackageAccommodation, Long>, JpaSpecificationExecutor<PackageAccommodation> {

    List<PackageAccommodation> findAllByPackageAccommodationIdEquals(Long packageAccommodationId);

    List<PackageAccommodation> findByPackages_PackageId(Long packageId);

    List<PackageAccommodation> findByAccommodation_AccommodationId(Long accommodationId);

    List<PackageAccommodation> findByPackages_PackageIdAndAccommodation_AccommodationId(Long packageId, Long accommodationId);
}
