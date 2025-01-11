package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.AccommodationGetResponseDTO;
import com.ceylone_fusion.booking_service.service.AccommodationService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accommodation")
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
}




