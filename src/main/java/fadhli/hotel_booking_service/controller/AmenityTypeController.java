package fadhli.hotel_booking_service.controller;

import fadhli.hotel_booking_service.dto.amenity_type.AmenityTypeRequestDto;
import fadhli.hotel_booking_service.dto.amenity_type.AmenityTypeResponseDto;
import fadhli.hotel_booking_service.service.AmenityTypeService;
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
@RequestMapping("/api/amenity-types")
@RequiredArgsConstructor
public class AmenityTypeController {
    private final AmenityTypeService amenityTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AmenityTypeResponseDto>>> getAmenityTypes() {
        List<AmenityTypeResponseDto> amenityTypes = amenityTypeService.findAllActive();
        ApiResponse<List<AmenityTypeResponseDto>> response = ApiResponse.success("Success", amenityTypes);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{amenityTypeId}")
    public ResponseEntity<ApiResponse<AmenityTypeResponseDto>> getAmenityTypeById(@PathVariable Long amenityTypeId) {
        AmenityTypeResponseDto amenityType = amenityTypeService.findById(amenityTypeId);
        ApiResponse<AmenityTypeResponseDto> response = ApiResponse.success("Success", amenityType);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AmenityTypeResponseDto>> createAmenityType(@Valid @RequestBody AmenityTypeRequestDto requestDto) {
        AmenityTypeResponseDto createdAmenityType = amenityTypeService.add(requestDto);
        ApiResponse<AmenityTypeResponseDto> response = ApiResponse.success(HttpStatus.CREATED.value(),"Amenity type has been created successfully", createdAmenityType);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{amenityTypeId}")
                .buildAndExpand(createdAmenityType.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{amenityTypeId}")
    public ResponseEntity<ApiResponse<AmenityTypeResponseDto>> updateAmenityType(
            @PathVariable Long amenityTypeId,
            @Valid @RequestBody AmenityTypeRequestDto requestDto) {
        AmenityTypeResponseDto updatedAmenityType = amenityTypeService.update(amenityTypeId, requestDto);
        ApiResponse<AmenityTypeResponseDto> response = ApiResponse.success("Amenity type has been updated successfully", updatedAmenityType);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{amenityTypeId}")
    public ResponseEntity<ApiResponse<Void>> deleteAmenityType(@PathVariable Long amenityTypeId) {
        amenityTypeService.removeById(amenityTypeId);
        ApiResponse<Void> response = ApiResponse.success("Amenity type has been deleted successfully", null);

        return ResponseEntity.ok(response);
    }
}
