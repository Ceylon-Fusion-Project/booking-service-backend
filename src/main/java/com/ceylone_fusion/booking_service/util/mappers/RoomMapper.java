package com.ceylone_fusion.booking_service.util.mappers;

import com.ceylone_fusion.booking_service.dto.RoomDTO;
import com.ceylone_fusion.booking_service.dto.response.RoomGetResponseDTO;
import com.ceylone_fusion.booking_service.entity.Room;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    List<RoomDTO> RoomEntityListToRoomDTOList(Page<Room> rooms);

    List<RoomGetResponseDTO> roomDTOListToRoomGetResponseDTOList(List<RoomDTO> roomDTOS);

}
