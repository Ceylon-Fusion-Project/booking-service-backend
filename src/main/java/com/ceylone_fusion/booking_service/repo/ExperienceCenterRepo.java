package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.ExperienceCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ExperienceCenterRepo extends JpaRepository<ExperienceCenter, Long>, JpaSpecificationExecutor<ExperienceCenter> {

    boolean existsByExperienceCodeEqualsIgnoreCase(String experienceName);

    List<ExperienceCenter> findAllByExperienceIdEquals(Long experienceId);

    List<ExperienceCenter> findAllByExperienceCodeEquals(String experienceCode);
}
