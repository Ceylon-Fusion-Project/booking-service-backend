package com.ceylone_fusion.booking_service.service;

import com.ceylone_fusion.booking_service.dto.RoomDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomSaveRequestDTO;
import com.ceylone_fusion.booking_service.dto.request.RoomUpdateRequestDTO;
import com.ceylone_fusion.booking_service.dto.response.RoomGetResponseDTO;


import java.util.List;

public interface RoomService {

    RoomDTO saveRoom(RoomSaveRequestDTO roomSaveRequestDTO);

    List<RoomGetResponseDTO> getAllRooms();

    List<RoomGetResponseDTO> getAllRoomDetailsById(Long roomId);

    List<RoomGetResponseDTO> getRoomDetailsByAccommodationId(Long accommodationId);

    RoomDTO updateRoomDetails(RoomUpdateRequestDTO roomUpdateRequestDTO, Long roomId);

    String deleteRoomById(Long roomId);
}
