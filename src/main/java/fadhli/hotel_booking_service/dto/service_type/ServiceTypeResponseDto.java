package fadhli.hotel_booking_service.dto.service_type;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ServiceTypeResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
