package fadhli.hotel_booking_service.dto.service_type;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceTypeRequestDto {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Default price must be non-negative")
    private BigDecimal price;

    private Boolean isActive;
}
