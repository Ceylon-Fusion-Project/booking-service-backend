package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.ExperienceCenterDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.ExperienceCenterUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.ExperienceCenterGetResponseDTO;
import com.ceylone_fusion.booking_service.service.ExperienceCenterService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping(
            path = "/get-experience-center-details-by-id",
            params = "id"
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
            path = "/update-experience-center-details",
            params = "id"
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
            path = "/delete-experience-center-by-id",
            params = "id"
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

}
