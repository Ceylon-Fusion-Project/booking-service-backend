package com.ceylone_fusion.booking_service.util.specifications;

import com.ceylone_fusion.booking_service.entity.Room;
import com.ceylone_fusion.booking_service.entity.enums.RoomType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecifications {

    public static Specification<Room> isAvailable(boolean isAvailable) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isAvailable"), isAvailable);
    }

    public static Specification<Room> hasType(RoomType roomType) {
        //lambda expression{root->entity, query->criteria, criteriaBuilder->build predicates (conditions)}
        return (root, query, criteriaBuilder) -> {
            if(roomType == null) {
                //no filter
                //a predicate that always evaluates to true
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("roomType"),
                    roomType
            );
        };
    }

    //filter by minimum and maximum price
    public static Specification<Room> hasPriceRange(Double minPrice, Double maxPrice) {
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
                        criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerNight"), minPrice)
                );

            }
            if(maxPrice != null) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("pricePerNight"), maxPrice)
                );
            }
            //return the combined predicates(all conditions)
            return predicate;
        };
    }
}
