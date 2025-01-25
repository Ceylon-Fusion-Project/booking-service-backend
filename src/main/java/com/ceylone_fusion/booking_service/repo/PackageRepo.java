package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PackageRepo extends JpaRepository<Package, Long>, JpaSpecificationExecutor<Package> {

    Package findPackageByPackageId(Long packageId);

    boolean existsByPackageNameIgnoreCase(String packageName);

    List<Package> findAllByPackageIdEquals(Long packageId);

    List<Package> findByPackageName(String packageName);

    List<Package> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Package> findByPriceGreaterThanEqual(Double minPrice);

    List<Package> findByPriceLessThanEqual(Double maxPrice);
}
