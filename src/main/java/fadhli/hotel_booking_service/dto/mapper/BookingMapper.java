package fadhli.hotel_booking_service.dto.mapper;

import fadhli.hotel_booking_service.dto.booking.BookingRequestDto;
import fadhli.hotel_booking_service.dto.booking.BookingResponseDto;
import fadhli.hotel_booking_service.entity.Booking;
import fadhli.hotel_booking_service.entity.Room;
import fadhli.hotel_booking_service.model.BookingStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {
    public BookingResponseDto toResponseDto(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setFirstName(booking.getFirstName());
        dto.setLastName(booking.getLastName());
        dto.setCheckedInDate(booking.getCheckedInDate());
        dto.setCheckedOutDate(booking.getCheckedOutDate());
        dto.setAdultCapacity(booking.getAdultCapacity());
        dto.setChildrenCapacity(booking.getChildrenCapacity());
        dto.setNight(booking.getNight());
        dto.setRoomId(booking.getRoom().getId());
        dto.setBookingStatus(booking.getBookingStatus());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());

        return dto;
    }

    public List<BookingResponseDto> toResponseDtos(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Booking toEntity(BookingRequestDto requestDto) {
        Booking booking = new Booking();

        booking.setFirstName(requestDto.getFirstName());
        booking.setLastName(requestDto.getLastName());
        booking.setCheckedInDate(requestDto.getCheckedInDate());
        booking.setCheckedOutDate(requestDto.getCheckedOutDate());
        booking.setAdultCapacity(requestDto.getAdultCapacity());
        booking.setChildrenCapacity(requestDto.getChildrenCapacity());
        booking.setBookingStatus(BookingStatus.BOOKED);

        return booking;
    }
}
