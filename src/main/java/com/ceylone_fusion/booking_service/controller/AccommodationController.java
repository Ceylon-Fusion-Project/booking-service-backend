package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationSaveRequestDTO;
import com.ceylone_fusion.booking_service.entity.Accommodation;
import com.ceylone_fusion.booking_service.service.AccommodationService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
