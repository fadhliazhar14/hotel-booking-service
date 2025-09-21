package fadhli.hotel_booking_service.dto.booking;

import fadhli.hotel_booking_service.entity.ServiceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingServiceRequestDto {
    @NotNull(message = "Booking ID is required")
    @Min(value = 1, message = "Booking ID must be at least 1")
    private Long bookingId;

    @NotNull(message = "Service type ID is required")
    @Min(value = 1, message = "Service type ID must be at least 1")
    private Long serviceTypeId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Size(max = 255, message = "Notes must not exceed 255 characters")
    private String notes;
}
