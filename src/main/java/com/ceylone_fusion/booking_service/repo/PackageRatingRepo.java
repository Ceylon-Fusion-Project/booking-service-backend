package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.Package;
import com.ceylone_fusion.booking_service.entity.PackageRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PackageRatingRepo extends JpaRepository<PackageRating, Long>, JpaSpecificationExecutor<PackageRating> {

    @Query("SELECT AVG(r.packageRating) FROM PackageRating r WHERE r.packages.packageId = :packageId")
    Double getAverageByPackageID(@Param("packageId") Long packageId);


    List<PackageRating> findAllByRatingIdEquals(Long ratingId);

    boolean existsByPackages_PackageId(Long packageId);

    Page<PackageRating> findAllByPackagesEquals(Package packagesByPackageIDEquals, Pageable pageRequest);


    Long countPackageRatingsByPackages_PackageId(Long packageId);

    boolean existsByCustomerEquals(Long customerId);

    Page<PackageRating> findAllByCustomerEquals(Long customerId, Pageable pageable);

    Long countPackageRatingByCustomerEquals(Long customerId);

    @Query("SELECT AVG(pr.packageRating) FROM PackageRating pr WHERE pr.packages.packageId = :packageId")
    Double getAverageByPackageId(@Param("packageId") Long packageId);

}
