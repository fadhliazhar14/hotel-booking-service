package fadhli.hotel_booking_service.dto.booking;

import fadhli.hotel_booking_service.model.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookingResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate checkedInDate;
    private LocalDate checkedOutDate;
    private Integer adultCapacity;
    private Integer childrenCapacity;
    private Integer night;
    private Long roomId;
    private Integer roomNumber;
    private BookingStatus bookingStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
