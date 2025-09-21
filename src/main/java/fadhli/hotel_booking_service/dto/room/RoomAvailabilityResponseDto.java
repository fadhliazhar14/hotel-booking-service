package fadhli.hotel_booking_service.dto.room;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomAvailabilityResponseDto {
    private Long id;
    private Integer roomNumber;
    private BigDecimal roomPrice;
    private Integer adultCapacity;
    private Integer childrenCapacity;
}
