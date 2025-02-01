package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.Package;
import com.ceylone_fusion.booking_service.entity.PackageRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PackageRepo extends JpaRepository<Package, Long>, JpaSpecificationExecutor<Package> {

    boolean existsByPackageNameIgnoreCase(String packageName);

    List<Package> findAllByPackageIdEquals(Long packageId);

    List<Package> findByPackageName(String packageName);

    Page<Package> findByPackageName(String packageName, Pageable pageable);

    List<Package> findByPricePerDayBetween(Double minPrice, Double maxPrice);

    Page<Package> findByPricePerDayBetween(Double minPrice, Double maxPrice, Pageable pageable);

    List<Package> findByPricePerDayGreaterThanEqual(Double minPrice);

    Page<Package> findByPricePerDayGreaterThanEqual(Double minPrice, Pageable pageable);

    List<Package> findByPricePerDayLessThanEqual(Double maxPrice);

    Page<Package> findByPricePerDayLessThanEqual(Double maxPrice, Pageable pageable);

    Package findByPackageId(Long packageId);

    Package findPackagesByPackageIdEquals(Long packageId);

}
