package fadhli.hotel_booking_service.dto.mapper;

import fadhli.hotel_booking_service.dto.room.RoomAmenityRequestDto;
import fadhli.hotel_booking_service.dto.room.RoomAmenityResponseDto;
import fadhli.hotel_booking_service.entity.RoomAmenity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomAmenityMapper {
    public RoomAmenityResponseDto toResponseDto(RoomAmenity roomAmenity) {
        RoomAmenityResponseDto dto = new RoomAmenityResponseDto();
        dto.setId(roomAmenity.getId());
        dto.setNotes(roomAmenity.getNotes());
        dto.setRoomId(roomAmenity.getRoom().getId());
        dto.setAmenityTypeId(roomAmenity.getAmenityType().getId());
        dto.setAmenityTypeName(roomAmenity.getAmenityType().getName());
        dto.setAmenityTypeDesc(roomAmenity.getAmenityType().getDescription());
        dto.setCreatedAt(roomAmenity.getCreatedAt());
        dto.setUpdatedAt(roomAmenity.getUpdatedAt());

        return dto;
    }

    public List<RoomAmenityResponseDto> toResponseDtos(List<RoomAmenity> roomAmenities) {
        return roomAmenities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public RoomAmenity toEntity(RoomAmenityRequestDto requestDto) {
        RoomAmenity roomAmenity = new RoomAmenity();
        roomAmenity.setNotes(requestDto.getNotes());

        return roomAmenity;
    }
}
