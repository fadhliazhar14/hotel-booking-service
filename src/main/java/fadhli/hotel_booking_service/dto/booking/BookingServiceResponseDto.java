package fadhli.hotel_booking_service.dto.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookingServiceResponseDto {
    private Long id;
    private Long bookingId;
    private Long serviceTypeId;
    private BigDecimal amount;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
