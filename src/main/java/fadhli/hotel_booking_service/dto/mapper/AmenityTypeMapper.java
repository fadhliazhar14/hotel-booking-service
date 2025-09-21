package fadhli.hotel_booking_service.dto.mapper;

import fadhli.hotel_booking_service.dto.amenity_type.AmenityTypeRequestDto;
import fadhli.hotel_booking_service.dto.amenity_type.AmenityTypeResponseDto;
import fadhli.hotel_booking_service.entity.AmenityType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AmenityTypeMapper {
    public AmenityTypeResponseDto toResponseDto(AmenityType amenityType) {
        AmenityTypeResponseDto dto = new AmenityTypeResponseDto();
        dto.setId(amenityType.getId());
        dto.setName(amenityType.getName());
        dto.setDescription(amenityType.getDescription());
        dto.setIsActive(amenityType.getIsActive());
        dto.setCreatedAt(amenityType.getCreatedAt());
        dto.setUpdatedAt(amenityType.getUpdatedAt());

        return dto;
    }

    public List<AmenityTypeResponseDto> toResponseDtos(List<AmenityType> roomAmenities) {
        return roomAmenities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public AmenityType toEntity(AmenityTypeRequestDto requestDTO) {
        AmenityType amenityType = new AmenityType();
        amenityType.setName(requestDTO.getName());
        amenityType.setDescription(requestDTO.getDescription());
        amenityType.setIsActive(requestDTO.getIsActive());

        return amenityType;
    }
}