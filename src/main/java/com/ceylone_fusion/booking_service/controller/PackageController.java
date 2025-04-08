package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.PackageDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedPackageGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageGetResponseDTO;
import com.ceylone_fusion.booking_service.service.PackageService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/package")
@CrossOrigin
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping(path = "/save-package")
    public ResponseEntity<StandardResponse> savePackage(@RequestBody PackageSaveRequestDTO packageSaveRequestDTO) {
        try {
            PackageDTO response = packageService.savePackage(packageSaveRequestDTO);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(201, "Package Saved Successfully", response.getPackageId()),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(409, e.getMessage(), null),
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping(path = "/get-all-packages")
    public ResponseEntity<StandardResponse> getAllPackages() {
        try {
            List<PackageGetResponseDTO> allPackages = packageService.getAllPackage();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Success", allPackages),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-all-packages-paginated")
    public ResponseEntity<StandardResponse> getAllPackage(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        try {
            // Pagination Specification
            PageRequest pageRequest = PageRequest.of(page, size);
            PaginatedPackageGetResponseDTO response = packageService.getAllPackagesPaginated(pageRequest);
            return new ResponseEntity<>(
                    new StandardResponse(200, "Package Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(
            path = "/get-package-details-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> getPackageById(@RequestParam(value = "id") Long packageId) {
        try {
            List<PackageGetResponseDTO> response = packageService.getPackageById(packageId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Package Found", response),
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
            path = "/get-package-details-by-code",
            params = "code"
    )
    public ResponseEntity<StandardResponse> getPackageByCode(@RequestParam(value = "code") String packageCode) {
        try {
            List<PackageGetResponseDTO> response = packageService.getPackageByCode(packageCode);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Package Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-package-details")
    public ResponseEntity<StandardResponse> getAllPackageDetails(
            @RequestParam(value = "packageName", required = false) String packageName,
            @RequestParam(value = "isPredefined", required = false) boolean isPredefined,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ){
        try {
            // Fetch package details using the service
            List<PackageGetResponseDTO> response =
                    packageService.getAllPackageDetails(packageName, isPredefined, minPrice, maxPrice);
            // Return successful response
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, " Package Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-package-details-paginated")
    public ResponseEntity<StandardResponse> getAllPackageDetails(
            @RequestParam(value = "packageName", required = false) String packageName,
            @RequestParam(value = "isPredefined", required = false) boolean isPredefined,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            PaginatedPackageGetResponseDTO response =
                    packageService.getAllPackageDetailsPaginated(packageName, isPredefined, minPrice, maxPrice, pageRequest);
            return new ResponseEntity<>(
                    new StandardResponse(200, "Package Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PatchMapping(
            path = "/update-package-details",
            params = "id"
    )
    public ResponseEntity<StandardResponse> updatePackageDetails(
            @RequestBody PackageUpdateRequestDTO packageUpdateRequestDTO,
            @RequestParam(value = "id") Long packageId
    ) {
        try {
            PackageDTO response = packageService.updatePackageDetails(packageUpdateRequestDTO, packageId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Package Updated Successfully", response.getPackageName()),
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
            path = "/delete-package-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> deletePackageById(@RequestParam(value = "id") Long packageId) {
        try {
            String response = packageService.deletePackageById(packageId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, response, "Package Deleted Successfully"),
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
