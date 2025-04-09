package com.ceylone_fusion.booking_service.service.serviceIMPL;


import com.ceylone_fusion.booking_service.dto.PackageRatingDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedPackageRatingGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageRatingSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageRatingUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageRatingGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.PackageRating;
import com.ceylone_fusion.booking_service.entity.Package;
import com.ceylone_fusion.booking_service.repo.PackageRatingRepo;
import com.ceylone_fusion.booking_service.repo.PackageRepo;
import com.ceylone_fusion.booking_service.service.PackageRatingService;
import com.ceylone_fusion.booking_service.util.mappers.PackageRatingMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageRatingServiceIMPL implements PackageRatingService {

    @Autowired
    private PackageRatingRepo packageRatingRepo;

    @Autowired
    private PackageRepo packageRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PackageRatingMapper packageRatingMapper;

    @Override
    public String savePackageRating(PackageRatingSaveRequestDTO packageRatingSaveRequestDTO) {
        Long packageId = packageRatingSaveRequestDTO.getPackageId();

        if(packageRepo.existsById(packageId)){

            PackageRating newPackageRating = new PackageRating(
                    packageRatingSaveRequestDTO.getCustomer(),
                    packageRatingSaveRequestDTO.getPackageRating(),
                    packageRatingSaveRequestDTO.getPackageReview(),
                    packageRepo.findByPackageId(packageId)
            );

            packageRatingRepo.save(newPackageRating);

            // Get the new average rating value
            Double newRatingValue = packageRatingRepo.getAverageByPackageID(packageId);

            // Update the package with the new average rating value
            Package packages = packageRepo.findByPackageId(packageId);

            packages.setPackageRatingValue(newRatingValue);
            packageRepo.save(packages);

            return "Package Rating of " + packages.getPackageName() + " is Updated!";
        }else{
            throw new RuntimeException("Package Not Found");
        }
    }

    @Override
    public List<PackageRatingGetResponseDTO> getPackageRatingById(Long ratingId) {
        List<PackageRating> packageRatings = packageRatingRepo.findAllByRatingIdEquals(ratingId);
        if(!packageRatings.isEmpty()) {
            return packageRatings.stream()
                    .map(rating -> {
                        PackageRatingGetResponseDTO packageRatingGetResponseDTO = modelMapper.map(rating, PackageRatingGetResponseDTO.class);
                        // Explicitly set packageId from the Package entity
                        packageRatingGetResponseDTO.setPackageId(rating.getPackages().getPackageId());
                        return packageRatingGetResponseDTO;
                    })
                    .collect(Collectors.toList());
        }
        else {
            throw new RuntimeException("No Package Rating Found");
        }
    }

    @Override
    public PaginatedPackageRatingGetResponseDTO getPackageRatingByPackageId(
            Long packageId,
            Pageable pageRequest
    ) {
        if (packageRatingRepo.existsByPackages_PackageId(packageId)) {
            // Get All Package Ratings by Package ID
            Page<PackageRating> packageRatings = packageRatingRepo
                    .findAllByPackagesEquals(packageRepo.findByPackageId(packageId), pageRequest);

            // Debugging Output
            packageRatings.getContent().forEach(rating ->
                    System.out.println("Rating ID: " + rating.getRatingId() +
                            ", Package ID: " + (rating.getPackages() != null ? rating.getPackages().getPackageId() : "NULL"))
            );

            // Map to DTO with explicit packageId assignment
            List<PackageRatingGetResponseDTO> ratingList = packageRatings.getContent()
                    .stream()
                    .map(rating -> {
                        PackageRatingGetResponseDTO dto = modelMapper.map(rating, PackageRatingGetResponseDTO.class);
                        dto.setPackageId(rating.getPackages() != null ? rating.getPackages().getPackageId() : null);
                        return dto;
                    })
                    .collect(Collectors.toList());

            return new PaginatedPackageRatingGetResponseDTO(
                    ratingList,
                    packageRatingRepo.countPackageRatingsByPackages_PackageId(packageId)
            );
        } else {
            throw new RuntimeException("Package Ratings Not Found");
        }
    }

    @Override
    public PaginatedPackageRatingGetResponseDTO getPackageRatingByCustomerId(
            Long customerId,
            Pageable pageable
    ) {
        if(packageRatingRepo.existsByCustomerEquals(customerId)){
            // Get All Package Ratings by Package ID
            Page<PackageRating> packageRatings = packageRatingRepo.
                    findAllByCustomerEquals(customerId, pageable);

            // Map Package Rating Entity List to PackageRatingGetAllByPackageDetailsResponseDTO List
            List<PackageRatingGetResponseDTO> ratingList = packageRatingMapper
                    .packageRatingEntityListToPackageRatingGetAllByUserDetailsResponseDTOList(
                            packageRatings.getContent()
                    );

            for (PackageRatingGetResponseDTO list : ratingList) {
                int index = ratingList.indexOf(list);
                list.setPackageId(packageRatings.getContent().get(index).getPackages().getPackageId());
            }

            System.out.println("ratingList = " + ratingList);

            return new PaginatedPackageRatingGetResponseDTO(
                    ratingList,
                    packageRatingRepo.countPackageRatingByCustomerEquals(customerId)
            );
        }else{
            throw new RuntimeException("Package Ratings Not Found");
        }
    }

    @Override
    public PackageRatingDTO updatePackageRating(
            PackageRatingUpdateRequestDTO packageRatingUpdateRequestDTO,
            Long packageRatingId
    ) {
        if(packageRatingRepo.existsById(packageRatingId)){
            // Get the existing package rating
            PackageRating existingPackageRating = packageRatingRepo.getReferenceById(packageRatingId);

            if(packageRatingUpdateRequestDTO.getPackageRating() != null){
                existingPackageRating.setPackageRating(packageRatingUpdateRequestDTO
                        .getPackageRating());
            }
            if(packageRatingUpdateRequestDTO.getPackageReview() != null){
                existingPackageRating.setPackageReview(packageRatingUpdateRequestDTO
                        .getPackageReview());
            }

            packageRatingRepo.save(existingPackageRating);

            // Get the new average rating value
            Double newRatingValue = packageRatingRepo
                    .getAverageByPackageID(existingPackageRating.getPackages().getPackageId());

            // Update the package with the new average rating value
            Package packages = packageRepo
                    .findPackagesByPackageIdEquals(existingPackageRating.getPackages().getPackageId());

            packages.setPackageRatingValue(newRatingValue);
            packageRepo.save(packages);

            //return "Package Rating of " + package.getPackageName() + " is Updated!";

            return packageRatingMapper.packageRatingEntityToPackageRatingDTO(existingPackageRating);
        }else{
            throw new RuntimeException("Package Rating Not Found");
        }
    }

    @Override
    public String deletePackageRatingById(Long packageRatingId) {
        // check if package Rating exists
        if (packageRatingRepo.existsById(packageRatingId)) {

            // Get the relevant package Id
            Long packageId = packageRatingRepo.getReferenceById(packageRatingId).getPackages().getPackageId();

            //delete package rating
            packageRatingRepo.deleteById(packageRatingId);

            // Get the new average rating value for the package
            Double newRatingValue = packageRatingRepo.getAverageByPackageId(packageId);

            // Update the package with the new average rating value
            Package packages = packageRepo.findPackagesByPackageIdEquals(packageId);

            packages.setPackageRatingValue(newRatingValue);
            packageRepo.save(packages);

            return "Rating Deleted Successfully and Package Rating of " + packages.getPackageName() + " is Updated!";
        } else {
            throw new RuntimeException("Package Rating Not Found");
        }
    }


}
