package fadhli.hotel_booking_service.service;

import fadhli.hotel_booking_service.dto.amenity_type.AmenityTypeRequestDto;
import fadhli.hotel_booking_service.dto.amenity_type.AmenityTypeResponseDto;
import fadhli.hotel_booking_service.dto.mapper.AmenityTypeMapper;
import fadhli.hotel_booking_service.entity.AmenityType;
import fadhli.hotel_booking_service.exception.BusinessValidationException;
import fadhli.hotel_booking_service.exception.ResourceNotFoundException;
import fadhli.hotel_booking_service.repository.AmenityTypeRepository;
import fadhli.hotel_booking_service.repository.RoomAmenityRepository;
import fadhli.hotel_booking_service.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AmenityTypeService {
    private final AmenityTypeRepository amenityTypeRepository;
    private final AmenityTypeMapper amenityTypeMapper;
    private final RoomAmenityRepository roomAmenityRepository;

    public List<AmenityTypeResponseDto> findAll() {
        return amenityTypeRepository.findAll(Sort.by("name")).stream()
                .map(amenityTypeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<AmenityTypeResponseDto> findAllActive() {
        return amenityTypeRepository.findByIsActiveTrue(Sort.by("name")).stream()
                .map(amenityTypeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public AmenityTypeResponseDto findById(Long amenityTypeId) {
        AmenityType amenityType = amenityTypeRepository.findById(amenityTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Amenity type with ID " + amenityTypeId + " not found."));

        return amenityTypeMapper.toResponseDto(amenityType);
    }

    public AmenityTypeResponseDto add(AmenityTypeRequestDto requestDto) {
        if (amenityTypeRepository.existsByName(requestDto.getName())) {
            throw new BusinessValidationException("Amenity type with name '" + requestDto.getName() + "' already exists.");
        }

        AmenityType amenityType = new AmenityType();
        amenityType.setName(requestDto.getName());
        amenityType.setDescription(requestDto.getDescription());
        amenityType.setIsActive(true);

        AmenityType saved = amenityTypeRepository.save(amenityType);

        return amenityTypeMapper.toResponseDto(saved);
    }

    public AmenityTypeResponseDto update(Long amenityTypeId, AmenityTypeRequestDto requestDto) {
        AmenityType amenityType = amenityTypeRepository.findById(amenityTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Amenity type with ID " + amenityTypeId + " not found."));

        if (!amenityType.getName().equals(requestDto.getName()) &&
                amenityTypeRepository.existsByName(requestDto.getName())) {
            throw new BusinessValidationException("Amenity type with name '" + requestDto.getName() + "' already exists.");
        }

        amenityType.setName(requestDto.getName() != null ? requestDto.getName() : amenityType.getName());
        amenityType.setDescription(requestDto.getDescription() != null ? requestDto.getDescription() : amenityType.getDescription());
        amenityType.setIsActive(requestDto.getIsActive() != null ? requestDto.getIsActive() : amenityType.getIsActive());

        AmenityType saved = amenityTypeRepository.save(amenityType);

        return amenityTypeMapper.toResponseDto(saved);
    }

    public void removeById(Long amenityTypeId) {
       AmenityType amenityType =  amenityTypeRepository.findById(amenityTypeId).orElseThrow(
                () -> new ResourceNotFoundException("Amenity type with ID " + amenityTypeId + " not found.")
        );

       // Checks if any room amenity using current amenity type
       if (roomAmenityRepository.findByAmenityTypeId(amenityTypeId).isEmpty()) {
           amenityTypeRepository.deleteById(amenityTypeId);
       } else {
           throw new BusinessValidationException("Cannot delete amenity type: it is still assigned to one or more room amenities.");
       }
    }
}
