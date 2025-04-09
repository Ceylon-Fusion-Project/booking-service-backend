package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.PackageRatingDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedPackageRatingGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageRatingSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageRatingUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageRatingGetResponseDTO;
import com.ceylone_fusion.booking_service.service.PackageRatingService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/package_ratings")
@CrossOrigin
public class PackageRatingController {

    @Autowired
    private PackageRatingService packageRatingService;

    @PostMapping(path = "/save-package-rating")
    public ResponseEntity<StandardResponse> savePackageRating(@RequestBody PackageRatingSaveRequestDTO packageRatingSaveRequestDTO) {
        try {
            String response = packageRatingService.savePackageRating(packageRatingSaveRequestDTO);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(201, "Add Rating Successfully", response),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(409, e.getMessage(), null),
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping(
            path = "/get-package-rating-details-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> getPackageRatingById(@RequestParam(value = "id") Long ratingId) {
        try {
            List<PackageRatingGetResponseDTO> response = packageRatingService.getPackageRatingById(ratingId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Package Rating Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-package-ratings-by-package-id")
    public ResponseEntity<StandardResponse> getPackageRatingsByPackageId(
            @RequestParam(value = "packageId") Long packageId,
            @RequestParam(value = "sort", required = false, defaultValue = "newest") String sort,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        try {

            // Sort Specification
            Sort sortSpec;
            switch (sort) {
                case "newest":
                    sortSpec = Sort.by("ratedAt").descending();
                    break;
                case "oldest":
                    sortSpec = Sort.by("ratedAt").ascending();
                    break;

                case "ratingAsc":
                    sortSpec = Sort.by("packageRating").ascending();
                    break;
                case "ratingDesc":
                    sortSpec = Sort.by("packageRating").descending();
                    break;
                default:
                    sortSpec = Sort.by("updatedDate").ascending();
            }

            // Pagination Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);

            PaginatedPackageRatingGetResponseDTO response = packageRatingService.getPackageRatingByPackageId(packageId,pageRequest);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Package Ratings Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-package-ratings-by-user-id")
    public ResponseEntity<StandardResponse> getPackageRatingsByUserId(
            @RequestParam(value = "customerId") Long customerId,
            @RequestParam(value = "sort", required = false, defaultValue = "newest") String sort,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        try {

            // Sort Specification
            Sort sortSpec;
            switch (sort) {
                case "newest":
                    sortSpec = Sort.by("ratedAt").descending();
                    break;
                case "oldest":
                    sortSpec = Sort.by("ratedAt").ascending();
                    break;

                case "ratingAsc":
                    sortSpec = Sort.by("packageRating").ascending();
                    break;
                case "ratingDesc":
                    sortSpec = Sort.by("packageRating").descending();
                    break;
                default:
                    sortSpec = Sort.by("ratedAt").ascending();
            }

            // Pagination Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);

            PaginatedPackageRatingGetResponseDTO response = packageRatingService.getPackageRatingByCustomerId(customerId,pageRequest);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Package Ratings Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PatchMapping(
            path = "/update-package-rating",
            params = "packageRatingId"
    )
    public ResponseEntity<StandardResponse> updatePackageRating(
            @RequestBody PackageRatingUpdateRequestDTO packageRatingUpdateRequestDTO,
            @RequestParam(value = "packageRatingId") Long packageRatingId
    ) {
        try {
            PackageRatingDTO response = packageRatingService.updatePackageRating(packageRatingUpdateRequestDTO, packageRatingId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Package Rating Updated Successfully", response.getRatedAt()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping(
            path = "delete-package-rating-by-id",
            params = "packageRatingId"
    )
    public ResponseEntity<StandardResponse> deletePackageRatingByID(
            @RequestParam(value = "packageRatingId") Long packageRatingId
    ) {
        try {
            String response = packageRatingService.deletePackageRatingById(packageRatingId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, response, "Package Rating Deleted Successfully"),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

}
