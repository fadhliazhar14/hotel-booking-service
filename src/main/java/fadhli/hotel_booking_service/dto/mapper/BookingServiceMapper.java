package fadhli.hotel_booking_service.dto.mapper;

import fadhli.hotel_booking_service.dto.booking.BookingServiceRequestDto;
import fadhli.hotel_booking_service.dto.booking.BookingServiceResponseDto;
import fadhli.hotel_booking_service.entity.Booking;
import fadhli.hotel_booking_service.entity.BookingService;
import fadhli.hotel_booking_service.entity.ServiceType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingServiceMapper {
    public BookingServiceResponseDto toResponseDto(BookingService bookingService) {
        BookingServiceResponseDto dto = new BookingServiceResponseDto();
        dto.setId(bookingService.getId());
        dto.setBookingId(bookingService.getBooking().getId());
        dto.setAmount(bookingService.getAmount());
        dto.setNotes(bookingService.getNotes());
        dto.setCreatedAt(bookingService.getCreatedAt());
        dto.setUpdatedAt(bookingService.getUpdatedAt());

        return dto;
    }

    public List<BookingServiceResponseDto> toResponseDtos(List<BookingService> bookingServices) {
        return bookingServices.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public BookingService toEntity(BookingServiceRequestDto requestDto) {
        BookingService bookingService = new BookingService();
        Booking booking = new Booking();
        ServiceType serviceType = new ServiceType();

        booking.setId(requestDto.getBookingId());
        serviceType.setId(requestDto.getServiceTypeId());

        bookingService.setBooking(booking);
        bookingService.setService_type(serviceType);
        bookingService.setAmount(requestDto.getAmount());
        bookingService.setNotes(requestDto.getNotes());

        return bookingService;
    }
}