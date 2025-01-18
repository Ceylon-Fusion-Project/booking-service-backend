package com.ceylone_fusion.booking_service.controller;

import com.ceylone_fusion.booking_service.dto.RoomDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedRoomGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.RoomGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.enums.RoomType;
import com.ceylone_fusion.booking_service.service.RoomService;
import com.ceylone_fusion.booking_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
            path = "/get-all-rooms-by-available",
            params = {"page", "size", "isAvailable"}
    )
    public ResponseEntity<StandardResponse> getAllRoomWithSort(
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
                    sortSpec = Sort.by("roomNumber").ascending();
                    break;
                case "nameDesc":
                    sortSpec = Sort.by("roomNumber").descending();
                    break;
                case "typeAsc":
                    sortSpec = Sort.by("roomType").ascending();
                    break;
                case "typeDesc":
                    sortSpec = Sort.by("roomType").descending();
                    break;
                case "priceAsc":
                    sortSpec = Sort.by("pricePerNight").ascending();
                    break;
                case "priceDesc":
                    sortSpec = Sort.by("pricePerNight").descending();
                    break;
                default:
                    sortSpec = Sort.by("roomNumber").ascending();
            }

            // Page Request Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);

            PaginatedRoomGetResponseDTO response = roomService.getAllRoomsSorted(isAvailable, pageRequest);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "All Rooms", response),
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


    @GetMapping(path = "/get-room-by-filtering")
    public ResponseEntity<StandardResponse> getRoomByFiltering(
            @RequestParam(required = false) RoomType roomType,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false,defaultValue = "true" ) boolean isAvailable,
            @RequestParam(value = "sort",required = false,defaultValue = "nameAsc") String sort,
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer size
    ) {
        try {
            // Sort Specification
            Sort sortSpec;
            switch (sort) {
                case "typeAsc":
                    sortSpec = Sort.by("roomType").ascending();
                    break;
                case "typeDesc":
                    sortSpec = Sort.by("roomType").descending();
                    break;
                case "priceAsc":
                    sortSpec = Sort.by("pricePerNight").ascending();
                    break;
                case "priceDesc":
                    sortSpec = Sort.by("pricePerNight").descending();
                    break;
                default:
                    sortSpec = Sort.by("roomType").ascending();
            }

            // Page Request Specification
            PageRequest pageRequest = PageRequest.of(page, size, sortSpec);
            PaginatedRoomGetResponseDTO response = roomService.getRoomByFiltering(roomType, minPrice, maxPrice, isAvailable, pageRequest);
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


}
