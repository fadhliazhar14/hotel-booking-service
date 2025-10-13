package fadhli.hotel_booking_service.service;

import fadhli.hotel_booking_service.dto.booking.*;
import fadhli.hotel_booking_service.dto.common.PageRequestDto;
import fadhli.hotel_booking_service.dto.common.PageResponseDto;
import fadhli.hotel_booking_service.dto.mapper.BookingMapper;
import fadhli.hotel_booking_service.dto.mapper.BookingServiceMapper;
import fadhli.hotel_booking_service.entity.Booking;
import fadhli.hotel_booking_service.entity.Room;
import fadhli.hotel_booking_service.exception.BusinessValidationException;
import fadhli.hotel_booking_service.exception.ResourceNotFoundException;
import fadhli.hotel_booking_service.model.BookingStatus;
import fadhli.hotel_booking_service.repository.BookingRepository;
import fadhli.hotel_booking_service.repository.BookingServiceRepository;
import fadhli.hotel_booking_service.repository.RoomRepository;
import fadhli.hotel_booking_service.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingServiceRepository bookingServiceRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;
    private final BookingServiceMapper bookingServiceMapper;

    public PageResponseDto<BookingResponseDto> findAllWithPagination(PageRequestDto pageRequest, String status) {
        Pageable pageable = PageUtil.createPageable(pageRequest);
        Page<Booking> bookingPage = bookingRepository.findAllWithPagination(
                pageRequest.getSearch(),
                status,
                pageable
        );

        List<BookingResponseDto> bookingResponses = bookingPage.getContent().stream()
                .map(bookingMapper::toResponseDto)
                .toList();

        return PageUtil.createPageResponse(
                bookingResponses,
                pageable,
                bookingPage.getTotalElements()
        );
    }

    public BookingResponseDto findById(Long bookingId) {
        Booking booking = findBookingById(bookingId);

        return bookingMapper.toResponseDto(booking);
    }

    public BookingResponseDto add(BookingRequestDto requestDto) {
        validateBookingRequest(requestDto);

        Room room = findRoomById(requestDto.getRoomId());

        // Check room availability
        if (!isRoomAvailable(room, requestDto.getCheckedInDate(), requestDto.getCheckedOutDate())) {
            throw new BusinessValidationException("Room is not available for the selected dates");
        }

        Booking booking = bookingMapper.toEntity(requestDto);
        booking.setRoom(room);
        booking.setTotalAmount(calculateTotalAmount(requestDto.getRoomId(), getNight(booking.getCheckedInDate(), booking.getCheckedOutDate())));
        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponseDto(savedBooking);
    }

    public BookingResponseDto updateStatus(Long bookingId, BookingStatusUpdateDto statusUpdateDto) {
        Booking booking = findBookingById(bookingId);

        // Validate status transition
        validateStatusTransition(booking.getBookingStatus(), statusUpdateDto.getBookingStatus());

        booking.setBookingStatus(statusUpdateDto.getBookingStatus());
        Booking updatedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponseDto(updatedBooking);
    }

    @Transactional
    public void removeById(Long bookingId) {
        // Check existing booking services
        List<fadhli.hotel_booking_service.entity.BookingService> services = bookingServiceRepository.findByBookingId(bookingId);

        if(!services.isEmpty()) {
            removeAllServicesFromBooking(bookingId);
        }

        Booking booking = findBookingById(bookingId);

        if (booking.getBookingStatus() == BookingStatus.CHECKED_IN) {
            throw new BusinessValidationException("Cannot delete a booking that is currently checked-in");
        }

        bookingRepository.deleteById(bookingId);
    }

    public BookingServiceResponseDto addServiceToBooking(Long bookingId, BookingServiceRequestDto requestDto) {
        Booking booking = findBookingById(bookingId);

        // Check if current booking status is eligible to add new service
        if(!booking.getBookingStatus().equals(BookingStatus.CHECKED_IN)) {
            throw new BusinessValidationException("Current booking status doesn't allow for additional service requests");
        }

        fadhli.hotel_booking_service.entity.BookingService service = bookingServiceMapper.toEntity(requestDto);

        return bookingServiceMapper.toResponseDto(bookingServiceRepository.save(service));
    }

    public void removeServiceFromBooking(Long bookingId, Long serviceId) {
        Booking booking = findBookingById(bookingId);

        // Check if current booking status is eligible to add service request
        if(!booking.getBookingStatus().equals(BookingStatus.CHECKED_IN)) {
            throw new BusinessValidationException("Current booking status doesn't allow to delete the service requests");
        }

        bookingServiceRepository.deleteById(serviceId);
    }

    public void removeAllServicesFromBooking(Long bookingId) {
        List<fadhli.hotel_booking_service.entity.BookingService> services = bookingServiceRepository.findByBookingId(bookingId);

        if(!services.isEmpty()) {
            bookingServiceRepository.deleteAllByBookingId(bookingId);
        }
    }

    /*** HELPER METHODS ***/

    private Booking findBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with ID " + bookingId + " not found"));
    }

    private Room findRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + roomId + " not found"));
    }

    private boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        LocalDate checkIn = checkInDate;
        LocalDate checkOut = checkOutDate;

        Optional<Room> availableRoom = roomRepository.findOneAvailableRoom(
                room.getAdultCapacity(),
                room.getChildrenCapacity(),
                checkIn,
                checkOut
        );

        return availableRoom.isPresent() && availableRoom.get().getId().equals(room.getId());
    }

    private BigDecimal calculateTotalAmount(Long roomId, int duration) {
        return roomRepository.findById(roomId)
                .map(r -> r.getRoomPrice().multiply(BigDecimal.valueOf(duration)))
                .orElse(BigDecimal.ZERO);
    }

    private int getNight(LocalDate checkInDate, LocalDate checkOutdate) {
        if (checkInDate != null && checkOutdate != null) {
            long diffInDays = ChronoUnit.DAYS.between(checkInDate, checkOutdate);

            return (int) Math.max(diffInDays, 1);
        }

        return 0;
    }

    private void validateBookingRequest(BookingRequestDto requestDto) {
        if (requestDto.getCheckedInDate() == null || requestDto.getCheckedOutDate() == null) {
            throw new BusinessValidationException("Check-in and check-out dates are required");
        }

        LocalDate checkIn = requestDto.getCheckedInDate();
        LocalDate checkOut = requestDto.getCheckedOutDate();

        if (checkIn.isAfter(checkOut)) {
            throw new BusinessValidationException("Check-out date must be after check-in date");
        }

        if (checkIn.isBefore(LocalDate.now())) {
            throw new BusinessValidationException("Check-in date cannot be in the past");
        }
    }

    private void validateStatusTransition(BookingStatus currentStatus, BookingStatus newStatus) {
        // Define valid status transitions
        boolean isValidTransition = switch (currentStatus) {
            case BOOKED -> newStatus == BookingStatus.CHECKED_IN || newStatus == BookingStatus.CANCELED;
            case CHECKED_IN -> newStatus == BookingStatus.CHECKED_OUT;
            case CHECKED_OUT, CANCELED -> false; // These are terminal states
        };

        if (!isValidTransition) {
            throw new BusinessValidationException(
                    String.format("Invalid status transition from %s to %s", currentStatus, newStatus));
        }
    }
}