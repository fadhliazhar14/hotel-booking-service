package fadhli.hotel_booking_service.dto.mapper;

import fadhli.hotel_booking_service.dto.service_type.ServiceTypeRequestDto;
import fadhli.hotel_booking_service.dto.service_type.ServiceTypeResponseDto;
import fadhli.hotel_booking_service.entity.ServiceType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceTypeMapper {
    public ServiceTypeResponseDto toResponseDto(ServiceType serviceType) {
        ServiceTypeResponseDto dto = new ServiceTypeResponseDto();
        dto.setId(serviceType.getId());
        dto.setName(serviceType.getName());
        dto.setDescription(serviceType.getDescription());
        dto.setPrice(serviceType.getPrice());
        dto.setIsActive(serviceType.getIsActive());
        dto.setCreatedAt(serviceType.getCreatedAt());
        dto.setUpdatedAt(serviceType.getUpdatedAt());

        return dto;
    }

    public List<ServiceTypeResponseDto> toResponseDTOs(List<ServiceType> serviceTypes) {
        return serviceTypes.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ServiceType toEntity(ServiceTypeRequestDto requestDTO) {
        ServiceType serviceType = new ServiceType();
        serviceType.setName(requestDTO.getName());
        serviceType.setDescription(requestDTO.getDescription());
        serviceType.setPrice(requestDTO.getPrice());
        serviceType.setIsActive(requestDTO.getIsActive());

        return serviceType;
    }
}
