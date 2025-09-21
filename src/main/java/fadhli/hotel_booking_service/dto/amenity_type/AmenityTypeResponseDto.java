package fadhli.hotel_booking_service.dto.amenity_type;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AmenityTypeResponseDto {
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
