package fadhli.hotel_booking_service.dto.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoomAmenityRequestDto {
    @Size(max = 255, message = "Notes must not exceed 255 characters")
    private String notes;

    @NotNull(message = "Amenity type ID is required")
    @Min(value = 1, message = "Amenity type ID must be at least 1")
    private Long amenityTypeId;
}
