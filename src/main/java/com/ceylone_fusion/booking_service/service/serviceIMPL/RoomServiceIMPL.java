package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.RoomDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.RoomGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Room;
import com.ceylone_fusion.booking_service.repo.AccommodationRepo;
import com.ceylone_fusion.booking_service.repo.RoomRepo;
import com.ceylone_fusion.booking_service.service.RoomService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceIMPL implements RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccommodationRepo accommodationRepo;


    @Override
    public RoomDTO saveRoom(RoomSaveRequestDTO roomSaveRequestDTO) {
        Long accommodationId = roomSaveRequestDTO.getAccommodationId();
        if (accommodationRepo.existsById(accommodationId)) {
            Room newRoom = new Room(
                    roomSaveRequestDTO.getRoomCode(),
                    roomSaveRequestDTO.getRoomNumber(),
                    roomSaveRequestDTO.getRoomType(),
                    roomSaveRequestDTO.getRoomImageURLs(),
                    roomSaveRequestDTO.getBeds(),
                    roomSaveRequestDTO.getPricePerNight(),
                    roomSaveRequestDTO.isAvailable(),
                    accommodationRepo.findAccommodationByAccommodationIdEquals(accommodationId)
            );

            roomRepo.save(newRoom);
            return modelMapper.map(newRoom, RoomDTO.class);
        } else {
            throw new RuntimeException("Accommodation Not Found");
        }

    }

    @Override
    public List<RoomGetResponseDTO> getAllRooms() {
        List<Room> getAllRooms = roomRepo.findAll();
        if(getAllRooms.size() > 0){
            List<RoomGetResponseDTO> roomGetResponseDTOS = modelMapper.map(getAllRooms, new TypeToken<List<RoomGetResponseDTO>>(){}.getType());
            return roomGetResponseDTOS;
        }
        else{
            throw new RuntimeException("No Rooms Found");
        }
    }

    @Override
    public List<RoomGetResponseDTO> getAllRoomDetailsById(Long roomId) {
        List<Room> rooms = roomRepo.findAllRoomsByRoomIdEquals(roomId);
        if(rooms.size() > 0) {
            List<RoomGetResponseDTO> roomGetResponseDTOS = modelMapper.map(rooms, new TypeToken<List<RoomGetResponseDTO>>(){}.getType());
            return roomGetResponseDTOS;
        }
        else {
            throw new RuntimeException("No Room Found");
        }
    }

    @Override
    public List<RoomGetResponseDTO> getRoomDetailsByAccommodationId(Long accommodationId) {
        if (roomRepo.existsByAccommodation_AccommodationId(accommodationId)) {
            List<Room> rooms = roomRepo.findRoomByAccommodationIn((accommodationRepo.findRoomsByAccommodationIdEquals(accommodationId)));
            if(rooms.size() > 0) {
                List<RoomGetResponseDTO> roomGetResponseDTOS = modelMapper.map(rooms, new TypeToken<List<RoomGetResponseDTO>>(){}.getType());
                return roomGetResponseDTOS;
            }
            else {
                throw new RuntimeException("No Room Found");
            }
        }
        else {
            throw new RuntimeException("No Accommodation Found");
        }
    }

    @Override
    public RoomDTO updateRoomDetails(
            RoomUpdateRequestDTO roomUpdateRequestDTO,
            Long roomId
    ) {
        //Get room by Room ID
        if (roomRepo.existsById(roomId)) {
            // Get Room by Room ID and Map Room Entity to Room DTO
            Room existingRoom = roomRepo.getReferenceById(roomId);

            // Update Room Code
            if (roomUpdateRequestDTO.getRoomCode() != null) {
                existingRoom.setRoomCode(roomUpdateRequestDTO.getRoomCode());
            }

            // Update Room Number
            if (roomUpdateRequestDTO.getRoomNumber() != 0) {
                existingRoom.setRoomNumber(roomUpdateRequestDTO.getRoomNumber());
            }

            // Update Room Type
            if (roomUpdateRequestDTO.getRoomType() != null) {
                existingRoom.setRoomType(roomUpdateRequestDTO.getRoomType());
            }

            // Update Room Image URLs
            if (roomUpdateRequestDTO.getRoomImageURLs() != null) {
                existingRoom.setRoomImageURLs(roomUpdateRequestDTO.getRoomImageURLs());
            }

            // Update Beds
            if (roomUpdateRequestDTO.getBeds() != 0) {
                existingRoom.setBeds(roomUpdateRequestDTO.getBeds());
            }

            // Update Price Per Night
            if (roomUpdateRequestDTO.getPricePerNight() != null) {
                existingRoom.setPricePerNight(roomUpdateRequestDTO.getPricePerNight());
            }

            // Update Is Available
            if (roomUpdateRequestDTO.isAvailable() != false ) {
                existingRoom.setAvailable(roomUpdateRequestDTO.isAvailable());
            }

            // Save the updated Room
            roomRepo.save(existingRoom);

            return modelMapper.map(existingRoom, RoomDTO.class);
        } else {
            throw new RuntimeException("Room Not Found");
        }
    }

    @Override
    public String deleteRoomById(Long roomId) {
        // Get Room by Accommodation ID
        if (roomRepo.existsById(roomId)) {
            String response = roomRepo.getReferenceById(roomId).getRoomNumber() + " Deleted!";

            //delete Room
            roomRepo.deleteById(roomId);
            return response;
        } else {
            throw new RuntimeException("Room Not Found");
        }
    }


}
