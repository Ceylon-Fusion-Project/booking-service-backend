package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.PackageExperienceDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageExperienceUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.PackageExperienceSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.PackageExperienceGetResponseDTO;
import com.ceylone_fusion.booking_service.service.PackageExperienceService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/package_experience")
@CrossOrigin
public class PackageExperienceController {

    @Autowired
    private PackageExperienceService packageExperienceService;

    @PostMapping(path = "/save-package-experience")
    public ResponseEntity<StandardResponse> savePackageExperience(@RequestBody PackageExperienceSaveRequestDTO packageExperienceSaveRequestDTO) {
        try {
            PackageExperienceDTO response = packageExperienceService.savePackageExperience(packageExperienceSaveRequestDTO);

            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(201, "Package Experience Saved Successfully", response.getPackageExperienceId()),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(409, e.getMessage(), null),
                    HttpStatus.CONFLICT
            );
        }
    }


    @GetMapping(path = "/get-all-package-experiences")
    public ResponseEntity<StandardResponse> getAllPackageExperiences() {
        try {
            List<PackageExperienceSaveRequestDTO> allPackageExperiences = packageExperienceService.getAllPackageExperiences();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Success", allPackageExperiences),
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
            path = "/get-package-experience-details-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> getPackageExperienceById(@RequestParam(value = "id") Long packageExperienceId) {
        try {
            List<PackageExperienceGetResponseDTO> response = packageExperienceService.getPackageExperienceById(packageExperienceId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Experience Package Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-package-experience-details")
    public ResponseEntity<StandardResponse> getAllPackageExperienceDetails(
            @RequestParam(value = "packageId", required = false) Long packageId,
            @RequestParam(value = "experienceId", required = false) Long experienceId
    ){
        try {
            // Fetch package-experience details using the service
            List<PackageExperienceGetResponseDTO> response =
                    packageExperienceService.getAllPackageExperienceDetails(packageId, experienceId);

            // Return successful response
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Experience Package Found", response),
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
            path = "/update-package-experience-details",
            params = "id"
    )
    public ResponseEntity<StandardResponse> updatePackageExperienceDetails(
            @RequestBody PackageExperienceUpdateRequestDTO packageExperienceUpdateRequestDTO,
            @RequestParam(value = "id") Long packageExperienceId
    ) {
        try {
            PackageExperienceDTO response = packageExperienceService.updatePackageExperienceDetails(packageExperienceUpdateRequestDTO, packageExperienceId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Experience Package Updated Successfully", response.getPackageExperienceId()),
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
            path = "/delete-package-experience-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> deletePackageExperienceById(@RequestParam(value = "id") Long packageExperienceId) {
        try {
            String response = packageExperienceService.deletePackageExperienceById(packageExperienceId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, response, "Experience Package Deleted Successfully"),
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
