package fadhli.hotel_booking_service.dto.room;

import fadhli.hotel_booking_service.entity.AmenityType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomAmenityResponseDto {
    private Long id;
    private String notes;
    private Long roomId;
    private Long amenityTypeId;
    private String amenityTypeName;
    private String amenityTypeDesc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
