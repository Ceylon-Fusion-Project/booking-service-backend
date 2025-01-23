package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface PackageRepo extends JpaRepository<Package, Long>, JpaSpecificationExecutor<Package> {

    Package findPackageByPackageId(Long packageId);

    boolean existsByPackageNameIgnoreCase(String packageName);

}
