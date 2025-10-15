package fadhli.hotel_booking_service.dto.room;

import fadhli.hotel_booking_service.entity.RoomAmenity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoomResponseDto {
    private Long id;
    private Integer roomNumber;
    private BigDecimal roomPrice;
    private Integer adultCapacity;
    private Integer childrenCapacity;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt;
}
