package fadhli.hotel_booking_service.controller;

import fadhli.hotel_booking_service.dto.service_type.ServiceTypeRequestDto;
import fadhli.hotel_booking_service.dto.service_type.ServiceTypeResponseDto;
import fadhli.hotel_booking_service.service.ServiceTypeService;
import fadhli.hotel_booking_service.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/service-types")
@RequiredArgsConstructor
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceTypeResponseDto>>> getServiceTypes() {
        List<ServiceTypeResponseDto> serviceTypes = serviceTypeService.findAllActive();
        ApiResponse<List<ServiceTypeResponseDto>> response = ApiResponse.success("Success", serviceTypes);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{serviceTypeId}")
    public ResponseEntity<ApiResponse<ServiceTypeResponseDto>> getServiceTypeById(@PathVariable Long serviceTypeId) {
        ServiceTypeResponseDto serviceType = serviceTypeService.findById(serviceTypeId);
        ApiResponse<ServiceTypeResponseDto> response = ApiResponse.success("Success", serviceType);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ServiceTypeResponseDto>> createServiceType(@Valid @RequestBody ServiceTypeRequestDto requestDto) {
        ServiceTypeResponseDto createdServiceType = serviceTypeService.add(requestDto);
        ApiResponse<ServiceTypeResponseDto> response = ApiResponse.success(HttpStatus.CREATED.value(),"Service type has been created successfully", createdServiceType);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{serviceTypeId}")
                .buildAndExpand(createdServiceType.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{serviceTypeId}")
    public ResponseEntity<ApiResponse<ServiceTypeResponseDto>> updateServiceType(
            @PathVariable Long serviceTypeId,
            @Valid @RequestBody ServiceTypeRequestDto requestDto) {
        ServiceTypeResponseDto updatedServiceType = serviceTypeService.update(serviceTypeId, requestDto);
        ApiResponse<ServiceTypeResponseDto> response = ApiResponse.success("Service type has been updated successfully", updatedServiceType);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{serviceTypeId}")
    public ResponseEntity<ApiResponse<Void>> deleteServiceType(@PathVariable Long serviceTypeId) {
        serviceTypeService.removeById(serviceTypeId);
        ApiResponse<Void> response = ApiResponse.success("Service type has been deleted successfully", null);

        return ResponseEntity.ok(response);
    }
}
