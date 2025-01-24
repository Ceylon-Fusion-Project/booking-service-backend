package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.PackageExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PackageExperienceRepo extends JpaRepository<PackageExperience, Long>, JpaSpecificationExecutor<PackageExperience> {

    List<PackageExperience> findAllByPackageExperienceIdEquals(Long packageExperienceId);

    List<PackageExperience> findByPackages_PackageIdAndExperience_ExperienceId(Long packageId, Long experienceId);

    List<PackageExperience> findByPackages_PackageId(Long packageId);

    List<PackageExperience> findByExperience_ExperienceId(Long experienceId);
}
