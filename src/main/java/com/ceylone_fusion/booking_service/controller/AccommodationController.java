package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedAccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.enums.AccommodationType;
import com.ceylone_fusion.booking_service.service.AccommodationService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accommodations")
@CrossOrigin
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    @PostMapping(path = "/save-accommodation")
    public ResponseEntity<StandardResponse> saveAccommodation(@RequestBody AccommodationSaveRequestDTO accommodationSaveRequestDTO) {
        try {
            AccommodationDTO response = accommodationService.saveAccommodation(accommodationSaveRequestDTO);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(201, "Accommodation Saved Successfully", response.getAccommodationName()),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(409, e.getMessage(), null),
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping(path = "/get-all-accommodations")
    public ResponseEntity<StandardResponse> getAllAccommodations() {
        try {
            List<AccommodationGetResponseDTO> allAccommodations = accommodationService.getAllAccommodations();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Success", allAccommodations),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-all-accommodations-paginated")
    public ResponseEntity<StandardResponse> getAllAccommodations(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        try {
            // Pagination Specification
            PageRequest pageRequest = PageRequest.of(page, size);
            PaginatedAccommodationGetResponseDTO response = accommodationService.getAllAccommodationsPaginated(pageRequest);
            return new ResponseEntity<>(
                    new StandardResponse(200, "Accommodations Found", response),
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
            path = "/get-all-accommodations-by-available",
            params = {"page", "size", "isAvailable"}
    )
    public ResponseEntity<StandardResponse> getAllAccommodationWithSort(
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
                    sortSpec = Sort.by("accommodationName").ascending();
                    break;
                case "nameDesc":
                    sortSpec = Sort.by("accommodationName").descending();
                    break;
                case "typeAsc":
                    sortSpec = Sort.by("accommodationType").ascending();
                    break;
                case "typeDesc":
                    sortSpec = Sort.by("accommodationType").descending();
                    break;
                case "locationAsc":
                    sortSpec = Sort.by("location").ascending();
                    break;
                case "locationDesc":
                    sortSpec = Sort.by("location").descending();
                    break;
                default:
                    sortSpec = Sort.by("accommodationName").ascending();
            }
            // Page Request Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);
            PaginatedAccommodationGetResponseDTO response = accommodationService.getAllAccommodationsSorted(isAvailable, pageRequest);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "All Accommodations", response),
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
            path = "/get-accommodation-details-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> getAccommodationById(@RequestParam(value = "id") Long accommodationId) {
        try {
            List<AccommodationGetResponseDTO> response = accommodationService.getAccommodationById(accommodationId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Accommodation Found", response),
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
            path = "/get-accommodation-details-by-code",
            params = "code"
    )
    public ResponseEntity<StandardResponse> getAccommodationByCode(@RequestParam(value = "code") String accommodationCode) {
        try {
            List<AccommodationGetResponseDTO> response = accommodationService.getAccommodationByCode(accommodationCode);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Accommodation Found", response),
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
            path = "/update-accommodation-details",
            params = "id"
    )
    public ResponseEntity<StandardResponse> updateAccommodationDetails(
            @RequestBody AccommodationUpdateRequestDTO accommodationUpdateRequestDTO,
            @RequestParam(value = "id") Long accommodationId
    ) {
        try {
            AccommodationDTO response = accommodationService.updateAccommodationDetails(accommodationUpdateRequestDTO, accommodationId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Accommodation Updated Successfully", response.getAccommodationName()),
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
            path = "/delete-accommodation-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> deleteAccommodationById(@RequestParam(value = "id") Long accommodationId) {
        try {
            String response = accommodationService.deleteAccommodationById(accommodationId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, response, "Accommodation Deleted Successfully"),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping(path = "/get-accommodation-by-filtering")
    public ResponseEntity<StandardResponse> getAccommodationByFiltering(
            @RequestParam(required = false) String accommodationName,
            @RequestParam(required = false) AccommodationType accommodationType,
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
                    sortSpec = Sort.by("accommodationName").ascending();
                    break;
                case "nameDesc":
                    sortSpec = Sort.by("accommodationName").descending();
                    break;
                case "typeAsc":
                    sortSpec = Sort.by("accommodationType").ascending();
                    break;
                case "typeDesc":
                    sortSpec = Sort.by("accommodationType").descending();
                    break;
                case "locationAsc":
                    sortSpec = Sort.by("location").ascending();
                    break;
                case "locationDesc":
                    sortSpec = Sort.by("location").descending();
                    break;
                default:
                    sortSpec = Sort.by("accommodationName").ascending();
            }
            // Page Request Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);
            PaginatedAccommodationGetResponseDTO response = accommodationService.getAccommodationByFiltering(accommodationName, accommodationType, location, isAvailable, pageRequest);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Accommodation Found", response),
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




