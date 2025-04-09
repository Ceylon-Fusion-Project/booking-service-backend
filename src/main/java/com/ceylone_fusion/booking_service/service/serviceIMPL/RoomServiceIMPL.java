package com.ceylone_fusion.booking_service.service.serviceIMPL;

import com.ceylone_fusion.booking_service.dto.RoomDTO;
import com.ceylone_fusion.booking_service.dto.paginated.PaginatedRoomGetResponseDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.RoomGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Room;
import com.ceylone_fusion.booking_service.entity.enums.RoomType;
import com.ceylone_fusion.booking_service.repo.AccommodationRepo;
import com.ceylone_fusion.booking_service.repo.RoomRepo;
import com.ceylone_fusion.booking_service.service.RoomService;
import com.ceylone_fusion.booking_service.util.mappers.RoomMapper;
import com.ceylone_fusion.booking_service.util.specifications.RoomSpecifications;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public RoomDTO saveRoom(RoomSaveRequestDTO roomSaveRequestDTO) {
        if(!roomRepo.existsByRoomCodeEqualsIgnoreCase(roomSaveRequestDTO.getRoomCode())){
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

                // Set isAvailable default true
                newRoom.setAvailable(true);

                roomRepo.save(newRoom);
                return modelMapper.map(newRoom, RoomDTO.class);
            } else {
                throw new RuntimeException("Accommodation Not Found");
            }
        } else {
            throw new RuntimeException(roomSaveRequestDTO.getRoomCode() + " is already exists");
        }
    }

    @Override
    public List<RoomGetResponseDTO> getAllRooms() {
        List<Room> getAllRooms = roomRepo.findAll();
        if(!getAllRooms.isEmpty()){
            return modelMapper.map(getAllRooms, new TypeToken<List<RoomGetResponseDTO>>(){}.getType());
        }
        else{
            throw new RuntimeException("No Rooms Found");
        }
    }

    @Override
    public PaginatedRoomGetResponseDTO getAllRoomsPaginated(Pageable pageable) {
        // Fetch paginated rooms
        Page<Room> roomsPage = roomRepo.findAll(pageable);
        if (roomsPage.hasContent()) {
            // Convert Page<Room> to List<RoomGetResponseDTO>
            List<RoomGetResponseDTO> roomGetResponseDTOS = roomsPage.getContent().stream()
                    .map(room -> modelMapper.map(room, RoomGetResponseDTO.class))
                    .toList();
            // Return paginated response
            return new PaginatedRoomGetResponseDTO(
                    roomGetResponseDTOS,
                    roomsPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Rooms Found");
        }
    }

    @Override
    public PaginatedRoomGetResponseDTO getAllRoomsSorted(boolean isAvailable, Pageable pageable) {
        // Get all rooms with is available
        Page<Room> rooms = roomRepo.findAllByIsAvailableEquals(isAvailable, pageable);
        if (!rooms.isEmpty()) {
            // Map Room Entity List to Room DTO List
            List<RoomDTO> roomDTOS = roomMapper.RoomEntityListToRoomDTOList(rooms);
            // Map Room DTO List to RoomGetResponseDTO List
            List<RoomGetResponseDTO> roomGetResponseDTOS = roomMapper.roomDTOListToRoomGetResponseDTOList(roomDTOS);
            // Return PaginatedRoomGetResponseDTO
            return new PaginatedRoomGetResponseDTO(
                    roomGetResponseDTOS,
                    roomRepo.countRoomByIsAvailableEquals(isAvailable)
            );
        } else {
            throw new RuntimeException("No Room Found");
        }
    }

    @Override
    public List<RoomGetResponseDTO> getAllRoomDetailsById(Long roomId) {
        List<Room> rooms = roomRepo.findAllRoomsByRoomIdEquals(roomId);
        if(!rooms.isEmpty()) {
            return modelMapper.map(rooms, new TypeToken<List<RoomGetResponseDTO>>(){}.getType());
        }
        else {
            throw new RuntimeException("No Room Found");
        }
    }

    @Override
    public List<RoomGetResponseDTO> getRoomDetailsByAccommodationId(Long accommodationId) {
        if (roomRepo.existsByAccommodation_AccommodationId(accommodationId)) {
            List<Room> rooms = roomRepo.findRoomByAccommodationIn((accommodationRepo.findRoomsByAccommodationIdEquals(accommodationId)));
            if(!rooms.isEmpty()) {
                return modelMapper.map(rooms, new TypeToken<List<RoomGetResponseDTO>>(){}.getType());
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
    public PaginatedRoomGetResponseDTO getRoomsByAccommodationIdPaginated(Long accommodationId, Pageable pageable) {
        // Fetch paginated rooms
        Page<Room> roomsPage = roomRepo.findByAccommodationId(accommodationId, pageable);
        if (roomsPage.hasContent()) {
            // Convert Page<Room> to List<RoomGetResponseDTO>
            List<RoomGetResponseDTO> roomGetResponseDTOS = roomsPage.getContent().stream()
                    .map(room -> modelMapper.map(room, RoomGetResponseDTO.class))
                    .toList();
            // Return paginated response
            return new PaginatedRoomGetResponseDTO(
                    roomGetResponseDTOS,
                    roomsPage.getTotalElements()
            );
        } else {
            throw new RuntimeException("No Rooms Found");
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
//            if (roomUpdateRequestDTO.isAvailable()) {
//                existingRoom.setAvailable(true);
//            }

            // Set isAvailable default true
            existingRoom.setAvailable(true);

            // Save the updated Room
            roomRepo.save(existingRoom);
            return modelMapper.map(existingRoom, RoomDTO.class);
        } else {
            throw new RuntimeException("Room Not Found");
        }
    }

    @Override
    public String deleteRoomById(Long roomId) {
        // Get Room by Room ID
        if (roomRepo.existsById(roomId)) {
            String response = roomRepo.getReferenceById(roomId).getRoomNumber() + " Deleted!";
            //delete Room
            roomRepo.deleteById(roomId);
            return response;
        } else {
            throw new RuntimeException("Room Not Found");
        }
    }

    @Override
    public PaginatedRoomGetResponseDTO getRoomByFiltering(
            RoomType roomType,
            Double minPrice,
            Double maxPrice,
            boolean isAvailable,
            Pageable pageable
    ) {
        Specification<Room> specification = Specification.
                where(RoomSpecifications.isAvailable(isAvailable))
                .and(RoomSpecifications.hasType(roomType))
                .and(RoomSpecifications.hasPriceRange(minPrice, maxPrice));
        // Get all rooms with available
        Page<Room> rooms = roomRepo.findAll(specification, pageable);
        if (!rooms.isEmpty()) {
            // Map Room Entity List to Room DTO List
            List<RoomDTO> roomDTOS = roomMapper.RoomEntityListToRoomDTOList(rooms);
            // Map Room DTO List to RoomGetResponseDTO List
            List<RoomGetResponseDTO> roomGetResponseDTOS = roomMapper
                    .roomDTOListToRoomGetResponseDTOList(roomDTOS);
            return new PaginatedRoomGetResponseDTO(
                    roomGetResponseDTOS,
                    roomRepo.count(specification)
            );
        } else {
            throw new RuntimeException("No Room Found");
        }
    }

}
