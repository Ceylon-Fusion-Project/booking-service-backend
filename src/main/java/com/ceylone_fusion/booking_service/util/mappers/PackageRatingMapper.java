package com.ceylone_fusion.booking_service.util.mappers;

import com.ceylone_fusion.booking_service.dto.PackageRatingDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageRatingGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.PackageRating;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PackageRatingMapper {

    List<PackageRatingGetResponseDTO> packageRatingEntityListToPackageRatingGetAllByUserDetailsResponseDTOList(List<PackageRating> content);

    PackageRatingDTO packageRatingEntityToPackageRatingDTO(PackageRating existingPackageRating);
}
