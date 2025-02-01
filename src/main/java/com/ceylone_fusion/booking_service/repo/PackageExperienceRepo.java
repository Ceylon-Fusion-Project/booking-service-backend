package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.PackageExperience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<PackageExperience> findByPackages_PackageIdAndExperience_ExperienceId(Long packageId, Long experienceId, Pageable pageable);

    List<PackageExperience> findByPackages_PackageId(Long packageId);

    Page<PackageExperience> findByPackages_PackageId(Long packageId, Pageable pageable);

    List<PackageExperience> findByExperience_ExperienceId(Long experienceId);

    Page<PackageExperience> findByExperience_ExperienceId(Long experienceId, Pageable pageable);

}
