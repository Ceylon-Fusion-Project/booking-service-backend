package com.ceylone_fusion.booking_service.util.specifications;

import com.ceylone_fusion.booking_service.entity.ExperienceCenter;
import org.springframework.data.jpa.domain.Specification;

public class ExperienceCenterSpecifications {

    public static Specification<ExperienceCenter> isAvailable(boolean isAvailable) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isAvailable"), isAvailable);
    }

    public static Specification<ExperienceCenter> hasName(String experienceName) {
        //lambda expression{root->entity, query->criteria, criteriaBuilder->build predicates (conditions)}
        return (root, query, criteriaBuilder) -> {
            if(experienceName == null || experienceName.isEmpty()) {
                //no filter
                //a predicate that always evaluates to true
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("experienceName")),
                    "%" + experienceName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<ExperienceCenter> hasLocation(String location) {
        //lambda expression{root->entity, query->criteria, criteriaBuilder->build predicates (conditions)}
        return (root, query, criteriaBuilder) -> {
            if(location == null || location.isEmpty()) {
                //no filter
                //a predicate that always evaluates to true
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("location")),
                    "%" + location.toLowerCase() + "%"
            );
        };
    }
}
