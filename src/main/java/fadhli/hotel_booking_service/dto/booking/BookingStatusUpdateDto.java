package fadhli.hotel_booking_service.dto.booking;

import fadhli.hotel_booking_service.model.BookingStatus;
import lombok.Data;

@Data
public class BookingStatusUpdateDto {
    private BookingStatus bookingStatus;
}
