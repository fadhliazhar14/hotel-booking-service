package fadhli.hotel_booking_service.service;

import fadhli.hotel_booking_service.dto.mapper.ServiceTypeMapper;
import fadhli.hotel_booking_service.dto.service_type.ServiceTypeRequestDto;
import fadhli.hotel_booking_service.dto.service_type.ServiceTypeResponseDto;
import fadhli.hotel_booking_service.entity.ServiceType;
import fadhli.hotel_booking_service.exception.BusinessValidationException;
import fadhli.hotel_booking_service.exception.ResourceNotFoundException;
import fadhli.hotel_booking_service.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ServiceTypeService {
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceTypeMapper serviceTypeMapper;

    public List<ServiceTypeResponseDto> findAll() {
        return serviceTypeRepository.findAll(Sort.by("name")).stream()
                .map(serviceTypeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ServiceTypeResponseDto> findAllActive() {
        return serviceTypeRepository.findByIsActiveTrue(Sort.by("name")).stream()
                .map(serviceTypeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public ServiceTypeResponseDto findById(Long id) {
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service type with ID " + id + " not found."));

        return serviceTypeMapper.toResponseDto(serviceType);
    }

    public ServiceTypeResponseDto add(ServiceTypeRequestDto requestDto) {
        if (serviceTypeRepository.existsByName(requestDto.getName())) {
            throw new BusinessValidationException("Service type with name '" + requestDto.getName() + "' already exists.");
        }

        ServiceType serviceType = new ServiceType();
        serviceType.setName(requestDto.getName());
        serviceType.setDescription(requestDto.getDescription());
        serviceType.setPrice(requestDto.getPrice());
        serviceType.setIsActive(true);

        ServiceType saved = serviceTypeRepository.save(serviceType);

        return serviceTypeMapper.toResponseDto(saved);
    }

    public ServiceTypeResponseDto update(Long id, ServiceTypeRequestDto requestDto) {
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service type with ID " + id + " not found."));

        if (!serviceType.getName().equals(requestDto.getName()) &&
                serviceTypeRepository.existsByName(requestDto.getName())) {
            throw new BusinessValidationException("Service type with name '" + requestDto.getName() + "' already exists.");
        }

        serviceType.setName(requestDto.getName() != null ? requestDto.getName() : serviceType.getName());
        serviceType.setDescription(requestDto.getDescription() != null ? requestDto.getDescription() : serviceType.getDescription());
        serviceType.setPrice(requestDto.getPrice() != null ? requestDto.getPrice() : serviceType.getPrice());
        serviceType.setIsActive(requestDto.getIsActive() != null ? requestDto.getIsActive() : serviceType.getIsActive());

        ServiceType saved = serviceTypeRepository.save(serviceType);

        return serviceTypeMapper.toResponseDto(saved);
    }

    public void removeById(Long id) {
        if (!serviceTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service type with ID " + id + " not found.");
        }
        serviceTypeRepository.deleteById(id);
    }
}
