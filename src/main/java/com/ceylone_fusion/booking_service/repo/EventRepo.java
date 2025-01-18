package com.ceylone_fusion.booking_service.repo;

import com.ceylone_fusion.booking_service.entity.Event;
import com.ceylone_fusion.booking_service.entity.ExperienceCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface EventRepo extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAllEventsByEventIdEquals(Long eventId);

    boolean existsByExperienceCenter_ExperienceId(Long experienceId);

    List<Event> findEventByExperienceCenterIn(List<ExperienceCenter> experienceCenters);

    Page<Event> findAllByIsAvailableEquals(boolean isAvailable, Pageable pageable);

    Long countEventByIsAvailableEquals(boolean isAvailable);
}
