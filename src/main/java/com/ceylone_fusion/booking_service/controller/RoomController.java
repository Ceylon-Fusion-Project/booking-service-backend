package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.AccommodationDTO;
import com.ceylone_fusion.booking_service.dto.RoomDTO;
import com.ceylone_fusion.booking_service.dto.request.AccommodationUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.RoomGetResponseDTO;
import com.ceylone_fusion.booking_service.service.AccommodationService;
import com.ceylone_fusion.booking_service.service.RoomService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/room")
@CrossOrigin
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private AccommodationService accommodationService;

    @PostMapping(path = "/save-room")
    public ResponseEntity<StandardResponse> saveRoom(@RequestBody RoomSaveRequestDTO roomSaveRequestDTO) {
        try {
            RoomDTO response = roomService.saveRoom(roomSaveRequestDTO);

            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(201, "Room Saved Successfully", response.getRoomNumber()),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(409, e.getMessage(), null),
                    HttpStatus.CONFLICT
            );
        }
    }


    @GetMapping(path = "/get-all-rooms")
    public ResponseEntity<StandardResponse> getAllRooms() {
        try {
            List<RoomGetResponseDTO> allRooms = roomService.getAllRooms();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Success", allRooms),
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
            path = "/get-room-details-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> getRoomById(@RequestParam(value = "id") Long roomId) {
        try {
            List<RoomGetResponseDTO> response = roomService.getAllRoomDetailsById(roomId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Room Found", response),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(404, e.getMessage(), null),
                    HttpStatus.NOT_FOUND
            );
        }
    }




    @GetMapping(path = "/get-rooms-by-accommodation-id")
    public ResponseEntity<StandardResponse> getRoomsByAccommodationId(@RequestParam(value = "id") Long accommodationId) {
        try {
            List<RoomGetResponseDTO> response = roomService.getRoomDetailsByAccommodationId(accommodationId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Room Found", response),
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
            path = "/update-room-details",
            params = "id"
    )
    public ResponseEntity<StandardResponse> updateRoomDetails(
            @RequestBody RoomUpdateRequestDTO roomUpdateRequestDTO,
            @RequestParam(value = "id") Long roomId
    ) {
        try {
            RoomDTO response = roomService.updateRoomDetails(roomUpdateRequestDTO, roomId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "Room Updated Successfully", response.getRoomNumber()),
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
            path = "/delete-room-by-id",
            params = "id"
    )
    public ResponseEntity<StandardResponse> deleteRoomById(@RequestParam(value = "id") Long roomId) {
        try {
            String response = roomService.deleteRoomById(roomId);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, response, "Room Deleted Successfully"),
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
