package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.ExperienceCenterDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedExperienceCenterGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.ExperienceCenterGetResponseDTO;
import com.ceylone_fusion.booking_service.service.ExperienceCenterService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/experience-center")
@CrossOrigin
public class ExperienceCenterController {

    @Autowired
    private ExperienceCenterService experienceCenterService;

    @PostMapping(path = "/save-experience-center")
    public ResponseEntity<StandardResponse> saveExperienceCenter(@RequestBody ExperienceCenterSaveRequestDTO experienceCenterSaveRequestDTO) {
        try {
            ExperienceCenterDTO response = experienceCenterService.saveExperienceCenter(experienceCenterSaveRequestDTO);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(201, "Experience Center Saved Successfully", response.getExperienceName()),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(409, e.getMessage(), null),
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping(path = "/get-all-experience-centers")
    public ResponseEntity<StandardResponse> getAllExperienceCenters() {
        try {
            List<ExperienceCenterGetResponseDTO> allExperienceCenters = experienceCenterService.getAllExperienceCenters();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Success", allExperienceCenters),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-all-experience-centers-paginated")
    public ResponseEntity<StandardResponse> getAllAccommodations(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        try {
            // Pagination Specification
            PageRequest pageRequest = PageRequest.of(page, size);
            PaginatedExperienceCenterGetResponseDTO response = experienceCenterService.getAllExperienceCentersPaginated(pageRequest);
            return new ResponseEntity<>(
                    new StandardResponse(200, "Experience Center Found", response),
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
            path = "/get-all-experience-center-by-available",
            params = {"page", "size", "isAvailable"}
    )
    public ResponseEntity<StandardResponse> getAllExperienceCenterWithSort(
            @RequestParam(value = "isAvailable",defaultValue = "true",required = false) boolean isAvailable,
            @RequestParam(value = "sort",required = false,defaultValue = "nameAsc") String sort,
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10",required = false) Integer size
    ) {
        try {
            // Sort Specification
            Sort sortSpec;
            switch (sort) {
                case "nameAsc":
                    sortSpec = Sort.by("experienceName").ascending();
                    break;
                case "nameDesc":
                    sortSpec = Sort.by("experienceName").descending();
                    break;
                case "locationAsc":
                    sortSpec = Sort.by("location").ascending();
                    break;
                case "locationDesc":
                    sortSpec = Sort.by("location").descending();
                    break;
                default:
                    sortSpec = Sort.by("experienceName").ascending();
            }
            // Page Request Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);
            PaginatedExperienceCenterGetResponseDTO response = experienceCenterService.getAllExperienceCentersSorted(isAvailable, pageRequest);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "All Experience Centers", response),
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
            path = "/get-experience-center-details-by-id"
            //params = "id"
    )
    public ResponseEntity<StandardResponse> getExperienceCenterById(@RequestParam(value = "id") Long experienceId) {
        try {
            List<ExperienceCenterGetResponseDTO> response = experienceCenterService.getExperienceCenterById(experienceId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Experience Center Found", response),
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
            path = "/get-experience-center-details-by-code",
            params = "code"
    )
    public ResponseEntity<StandardResponse> getExperienceCenterByCode(@RequestParam(value = "code") String experienceCode) {
        try {
            List<ExperienceCenterGetResponseDTO> response = experienceCenterService.getExperienceCenterByCode(experienceCode);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Experience Center Found", response),
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
            path = "/update-experience-center-details"
            //params = "id"
    )
    public ResponseEntity<StandardResponse> updateExperienceCenterDetails(
            @RequestBody ExperienceCenterUpdateRequestDTO experienceCenterUpdateRequestDTO,
            @RequestParam(value = "id") Long experienceId
    ) {
        try {
            ExperienceCenterDTO response = experienceCenterService.updateExperienceCenterDetails(experienceCenterUpdateRequestDTO, experienceId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Experience Center Updated Successfully", response.getExperienceId()),
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
            path = "/delete-experience-center-by-id"
            //params = "id"
    )
    public ResponseEntity<StandardResponse> deleteExperienceCenterById(@RequestParam(value = "id") Long experienceId) {
        try {
            String response = experienceCenterService.deleteExperienceCenterById(experienceId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, response, "Experience Center Deleted Successfully"),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-experience-center-by-filtering")
    public ResponseEntity<StandardResponse> getExperienceCenterByFiltering(
            @RequestParam(required = false) String experienceName,
            @RequestParam(required = false) String location,
            @RequestParam(required = false,defaultValue = "true" ) boolean isAvailable,
            @RequestParam(value = "sort",required = false,defaultValue = "nameAsc") String sort,
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer size
    ) {
        try {
            // Sort Specification
            Sort sortSpec;
            switch (sort) {
                case "nameAsc":
                    sortSpec = Sort.by("experienceName").ascending();
                    break;
                case "nameDesc":
                    sortSpec = Sort.by("experienceName").descending();
                    break;
                case "locationAsc":
                    sortSpec = Sort.by("location").ascending();
                    break;
                case "locationDesc":
                    sortSpec = Sort.by("location").descending();
                    break;
                default:
                    sortSpec = Sort.by("experienceName").ascending();
            }
            // Page Request Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);
            PaginatedExperienceCenterGetResponseDTO response = experienceCenterService.getExperienceCenterByFiltering(experienceName, location, isAvailable, pageRequest);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Experience Center Found", response),
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
