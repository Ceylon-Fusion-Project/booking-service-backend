package com.ceylone_fusion.booking_service.util.specifications;

import com.ceylone_fusion.booking_service.entity.Accommodation;
import com.ceylone_fusion.booking_service.entity.enums.AccommodationType;
import org.springframework.data.jpa.domain.Specification;

public class AccommodationSpecifications {

    public static Specification<Accommodation> isAvailable(boolean isAvailable) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isAvailable"), isAvailable);
    }

    //filter by accommodation name
    public static Specification<Accommodation> hasName(String accommodationName) {
        //lambda expression{root->entity, query->criteria, criteriaBuilder->build predicates (conditions)}
        return (root, query, criteriaBuilder) -> {
            if(accommodationName == null || accommodationName.isEmpty()) {
                //no filter
                //a predicate that always evaluates to true
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("accommodationName")),
                    "%" + accommodationName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Accommodation> hasType(AccommodationType accommodationType) {
        //lambda expression{root->entity, query->criteria, criteriaBuilder->build predicates (conditions)}
        return (root, query, criteriaBuilder) -> {
            if(accommodationType == null) {
                //no filter
                //a predicate that always evaluates to true
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("accommodationType"),
                    accommodationType
            );
        };
    }

}
