package com.ceylone_fusion.booking_service.util.specifications;

import com.ceylone_fusion.booking_service.entity.Event;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
//import java.time.LocalTime;

public class EventSpecifications {
    
    public static Specification<Event> isAvailable(boolean isAvailable) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isAvailable"), isAvailable);
    }

    public static Specification<Event> hasName(String eventName) {
        //lambda expression{root->entity, query->criteria, criteriaBuilder->build predicates (conditions)}
        return (root, query, criteriaBuilder) -> {
            if(eventName == null || eventName.isEmpty()) {
                //no filter
                //a predicate that always evaluates to true
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("eventName")),
                    "%" + eventName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Event> hasPriceRange(Double minPrice, Double maxPrice) {
        //lambda expression
        return (root, query, criteriaBuilder) -> {
            if(minPrice == null && maxPrice == null) {
                //no filter
                return criteriaBuilder.conjunction();
            }
            //create initial predicate(clause in a SQL WHERE statement)
            Predicate predicate = criteriaBuilder.conjunction();
            if(minPrice != null) {
                //combine predicates using and
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerEvent"), minPrice)
                );
            }
            if(maxPrice != null) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("pricePerEvent"), maxPrice)
                );
            }
            //return the combined predicates(all conditions)
            return predicate;
        };
    }

    public static Specification<Event> hasTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return (root, query, criteriaBuilder) -> {
            if(startTime == null && endTime == null) {
                return criteriaBuilder.conjunction();
            }
            Predicate predicate = criteriaBuilder.conjunction();
            if(startTime != null) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), startTime)
                );
            }
            if(endTime != null) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("endTime"), endTime)
                );
            }
            return predicate;
        };
    }

}
