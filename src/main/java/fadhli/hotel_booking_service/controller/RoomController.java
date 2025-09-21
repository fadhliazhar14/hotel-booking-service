package fadhli.hotel_booking_service.controller;

import fadhli.hotel_booking_service.dto.common.PageRequestDto;
import fadhli.hotel_booking_service.dto.common.PageResponseDto;
import fadhli.hotel_booking_service.dto.room.*;
import fadhli.hotel_booking_service.service.RoomService;
import fadhli.hotel_booking_service.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponseDto<RoomResponseDto>>> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String search) {

        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSort(sort);
        pageRequest.setDirection(direction);
        pageRequest.setSearch(search);

        PageResponseDto<RoomResponseDto> rooms = roomService.findAllWithPagination(pageRequest);
        ApiResponse<PageResponseDto<RoomResponseDto>> response = ApiResponse.success("Success", rooms);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponseDto>> getRoomById(@PathVariable Long roomId) {
        RoomResponseDto room = roomService.findById(roomId);
        ApiResponse<RoomResponseDto> response = ApiResponse.success("Success", room);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/available-room")
    public ResponseEntity<ApiResponse<RoomAvailabilityResponseDto>> checkAvailableRoom(@Valid @RequestBody RoomAvailabilityRequestDto requestDto) {
        RoomAvailabilityResponseDto availableRoom = roomService.getAvailableRoom(requestDto);
        ApiResponse<RoomAvailabilityResponseDto> response = ApiResponse.success("Success", availableRoom);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoomResponseDto>> createRoom(@Valid @RequestBody RoomRequestDto request) {
        RoomResponseDto savedRoom = roomService.add(request);
        ApiResponse<RoomResponseDto> response = ApiResponse.success(HttpStatus.CREATED.value(), "Room has been created successfully", savedRoom);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{roomId}")
                .buildAndExpand(savedRoom.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponseDto>> updateRoom(
            @PathVariable Long roomId,
            @Valid @RequestBody RoomRequestDto requestDto) {
        RoomResponseDto room = roomService.update(roomId, requestDto);
        ApiResponse<RoomResponseDto> response = ApiResponse.success("Room has been updated successfully", room);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable Long roomId) {
        roomService.removeById(roomId);
        ApiResponse<Void> response = ApiResponse.success(HttpStatus.OK.value(), "Room has been deleted successfully", null);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/amenities")
    public ResponseEntity<ApiResponse<List<RoomAmenityResponseDto>>> getRoomAmenities(@PathVariable Long roomId) {
        List<RoomAmenityResponseDto> roomAmenities = roomService.getAmenities(roomId);
        ApiResponse<List<RoomAmenityResponseDto>> response = ApiResponse.success("Success", roomAmenities);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{roomId}/amenities")
    public ResponseEntity<ApiResponse<RoomAmenityResponseDto>> createAmenity(
            @PathVariable Long roomId,
            @RequestBody RoomAmenityRequestDto requestDto) {
        RoomAmenityResponseDto createdRoomAmenity = roomService.addAmenity(roomId, requestDto);
        ApiResponse<RoomAmenityResponseDto> response = ApiResponse.success(HttpStatus.CREATED.value(), "Room amenity has been added successfully", createdRoomAmenity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{roomAmenityId}")
                .buildAndExpand(createdRoomAmenity.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{roomId}/amenities/{roomAmenityId}")
    public ResponseEntity<ApiResponse<Void>> deleteAmenity(
            @PathVariable Long roomId,
            @PathVariable Long roomAmenityId) {
        roomService.removeAmenity(roomId, roomAmenityId);
        ApiResponse<Void> response = ApiResponse.success("Room amenity has been deleted successfully", null);

        return ResponseEntity.ok(response);
    }
}
