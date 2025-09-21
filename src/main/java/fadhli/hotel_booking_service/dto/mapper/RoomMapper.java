package fadhli.hotel_booking_service.dto.mapper;

import fadhli.hotel_booking_service.dto.room.RoomAvailabilityResponseDto;
import fadhli.hotel_booking_service.dto.room.RoomRequestDto;
import fadhli.hotel_booking_service.dto.room.RoomResponseDto;
import fadhli.hotel_booking_service.entity.Room;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public RoomResponseDto toResponseDto(Room room) {
        RoomResponseDto dto = new RoomResponseDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomPrice(room.getRoomPrice());
        dto.setAdultCapacity(room.getAdultCapacity());
        dto.setChildrenCapacity(room.getChildrenCapacity());
        dto.setCreatedAt(room.getCreatedAt());
        dto.setUpdatedAt(room.getUpdatedAt());

        return dto;
    }

    public List<RoomResponseDto> toResponseDtos(List<Room> rooms) {
        return rooms.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Room toEntity(RoomRequestDto requestDto) {
        Room room = new Room();
        room.setRoomNumber(requestDto.getRoomNumber());
        room.setRoomPrice(requestDto.getRoomPrice());
        room.setAdultCapacity(requestDto.getAdultCapacity());
        room.setChildrenCapacity(requestDto.getChildrenCapacity());

        return room;
    }

    public RoomAvailabilityResponseDto toRoomAvailabilityResponseDto(Room availableRoom) {
        if (availableRoom == null) {
            return null;
        }

        RoomAvailabilityResponseDto dto = new RoomAvailabilityResponseDto();
        dto.setId(availableRoom.getId());
        dto.setRoomNumber(availableRoom.getRoomNumber());
        dto.setRoomPrice(availableRoom.getRoomPrice());
        dto.setAdultCapacity(availableRoom.getAdultCapacity());
        dto.setChildrenCapacity(availableRoom.getChildrenCapacity());

        return dto;
    }
}
