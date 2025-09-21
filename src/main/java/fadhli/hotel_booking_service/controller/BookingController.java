package fadhli.hotel_booking_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fadhli.hotel_booking_service.dto.booking.*;
import fadhli.hotel_booking_service.dto.common.PageRequestDto;
import fadhli.hotel_booking_service.dto.common.PageResponseDto;
import fadhli.hotel_booking_service.service.BookingService;
import fadhli.hotel_booking_service.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
public ResponseEntity<ApiResponse<PageResponseDto<BookingResponseDto>>> getBookings(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(defaultValue = "id") String sort,
        @RequestParam(defaultValue = "desc") String direction,
        @RequestParam(required = false) String search) throws JsonProcessingException {

        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSort(sort);
        pageRequest.setDirection(direction);
        pageRequest.setSearch(search);

        PageResponseDto<BookingResponseDto> bookings = bookingService.findAllWithPagination(pageRequest);
        ApiResponse<PageResponseDto<BookingResponseDto>> response = ApiResponse.success("Success", bookings);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponseDto>> getBookingById(
            @PathVariable Long bookingId) {
        BookingResponseDto booking = bookingService.findById(bookingId);
        ApiResponse<BookingResponseDto> response = ApiResponse.success("Success", booking);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponseDto>> createBooking(@Valid @RequestBody BookingRequestDto request) {
        BookingResponseDto savedBooking = bookingService.add(request);
        ApiResponse<BookingResponseDto> response = ApiResponse.success(201, "Booking has been created successfully", savedBooking);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{bookingId}")
                .buildAndExpand(savedBooking.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponseDto>> updateBooking(
            @PathVariable Long bookingId,
            @Valid @RequestBody BookingRequestDto request) {
        BookingResponseDto updatedBooking = bookingService.update(bookingId, request);
        ApiResponse<BookingResponseDto> response = ApiResponse.success("Booking updated successfully", updatedBooking);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponseDto>> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestBody BookingStatusUpdateDto statusUpdateDto) {
        BookingResponseDto updatedBooking = bookingService.updateStatus(bookingId, statusUpdateDto);
        ApiResponse<BookingResponseDto> response = ApiResponse.success("Booking status updated successfully", updatedBooking);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Void>> deleteBooking(
            @PathVariable Long bookingId) {
        bookingService.removeById(bookingId);
        ApiResponse<Void> response = ApiResponse.success("Booking has been deleted successfully", null);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{bookingId}/services")
    public ResponseEntity<ApiResponse<BookingServiceResponseDto>> createService(
            @PathVariable Long bookingId,
            @RequestBody BookingServiceRequestDto dto) {
        BookingServiceResponseDto createdBookingService = bookingService.addServiceToBooking(bookingId, dto);
        ApiResponse<BookingServiceResponseDto> response = ApiResponse.success(HttpStatus.CREATED.value(), "Booking service has been added successfully", createdBookingService);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{bookingServiceId}")
                .buildAndExpand(createdBookingService.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }


    @DeleteMapping("/{bookingId}/services/{bookingServiceId}")
    public ResponseEntity<ApiResponse<Void>> deleteService(
            @PathVariable Long bookingId,
            @PathVariable Long bookingServiceId) {
        bookingService.removeServiceFromBooking(bookingId, bookingServiceId);
        ApiResponse<Void> response = ApiResponse.success("Booking service has been deleted successfully", null);

        return ResponseEntity.ok(response);
    }
}
