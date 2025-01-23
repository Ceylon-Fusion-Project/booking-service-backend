package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.PackageAccommodationDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageAccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageAccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageAccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.service.PackageAccommodationService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/package_accommodation")
@CrossOrigin
public class PackageAccommodationController {

    @Autowired
    private PackageAccommodationService packageAccommodationService;

    @PostMapping(path = "/save-package-accommodation")
    public ResponseEntity<StandardResponse> savePackageAccommodation(@RequestBody PackageAccommodationSaveRequestDTO packageAccommodationSaveRequestDTO) {
        try {
            PackageAccommodationDTO response = packageAccommodationService.savePackageAccommodation(packageAccommodationSaveRequestDTO);

            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(201, "Package Accommodation Saved Successfully", response.getPackageAccommodationId()),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(409, e.getMessage(), null),
                    HttpStatus.CONFLICT
            );
        }
    }


    @GetMapping(path = "/get-all-package-accommodations")
    public ResponseEntity<StandardResponse> getAllPackageAccommodations() {
        try {
            List<PackageAccommodationSaveRequestDTO> allPackageAccommodations = packageAccommodationService.getAllPackageAccommodations();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Success", allPackageAccommodations),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @GetMapping(
            path = "/get-package-accommodation-details-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> getPackageAccommodationById(@RequestParam(value = "id") Long packageAccommodationId) {
        try {
            List<PackageAccommodationGetResponseDTO> response = packageAccommodationService.getPackageAccommodationById(packageAccommodationId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Accommodation Package Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-package-accommodation-details")
    public ResponseEntity<StandardResponse> getAllPackageAccommodationDetails(
            @RequestParam(value = "packageId", required = false) Long packageId,
            @RequestParam(value = "accommodationId", required = false) Long accommodationId
    ){
        try {
            // Fetch package-accommodation details using the service
            List<PackageAccommodationGetResponseDTO> response =
                    packageAccommodationService.getAllPackageAccommodationDetails(packageId, accommodationId);

            // Return successful response
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Accommodation Package Found", response),
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
            path = "/update-package-accommodation-details",
            params = "id"
    )
    public ResponseEntity<StandardResponse> updatePackageAccommodationDetails(
            @RequestBody PackageAccommodationUpdateRequestDTO packageAccommodationUpdateRequestDTO,
            @RequestParam(value = "id") Long packageAccommodationId
    ) {
        try {
            PackageAccommodationDTO response = packageAccommodationService.updatePackageAccommodationDetails(packageAccommodationUpdateRequestDTO, packageAccommodationId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Accommodation Package Updated Successfully", response.getPackageAccommodationId()),
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
            path = "/delete-package-accommodation-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> deletePackageAccommodationById(@RequestParam(value = "id") Long packageAccommodationId) {
        try {
            String response = packageAccommodationService.deletePackageAccommodationById(packageAccommodationId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, response, "Accommodation Package Deleted Successfully"),
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
