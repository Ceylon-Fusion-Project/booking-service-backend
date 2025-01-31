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

    List<Package> findByPricePerDayBetween(Double minPrice, Double maxPrice);

    List<Package> findByPricePerDayGreaterThanEqual(Double minPrice);

    List<Package> findByPricePerDayLessThanEqual(Double maxPrice);

    Package findByPackageId(Long packageId);

    Package findPackagesByPackageIdEquals(Long packageId);

}
