package fadhli.hotel_booking_service.service;

import fadhli.hotel_booking_service.dto.common.PageRequestDto;
import fadhli.hotel_booking_service.dto.common.PageResponseDto;
import fadhli.hotel_booking_service.dto.mapper.RoomAmenityMapper;
import fadhli.hotel_booking_service.dto.mapper.RoomMapper;
import fadhli.hotel_booking_service.dto.room.*;
import fadhli.hotel_booking_service.entity.AmenityType;
import fadhli.hotel_booking_service.entity.Room;
import fadhli.hotel_booking_service.entity.RoomAmenity;
import fadhli.hotel_booking_service.exception.BusinessValidationException;
import fadhli.hotel_booking_service.exception.ResourceNotFoundException;
import fadhli.hotel_booking_service.repository.AmenityTypeRepository;
import fadhli.hotel_booking_service.repository.RoomAmenityRepository;
import fadhli.hotel_booking_service.repository.RoomRepository;
import fadhli.hotel_booking_service.util.PageUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final AmenityTypeRepository amenityTypeRepository;
    private final RoomAmenityRepository roomAmenityRepository;
    private final RoomMapper roomMapper;
    private final RoomAmenityMapper roomAmenityMapper;

    public PageResponseDto<RoomResponseDto> findAllWithPagination(PageRequestDto pageRequest) {
        Pageable pageable = PageUtil.createPageable(pageRequest);

        Page<Room> roomPage = roomRepository.findAllWithPagination(pageRequest.getSearch(), pageable);

        List<RoomResponseDto> rooms = roomPage.getContent().stream()
                .map(roomMapper::toResponseDto)
                .toList();


        return PageUtil.createPageResponse(
                rooms,
                pageable,
                roomPage.getTotalElements()
        );
    }

    public RoomResponseDto findById(Long roomId) {
        return roomMapper.toResponseDto(roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + roomId + " not found."))
        );
    }

    public RoomAvailabilityResponseDto getAvailableRoom(RoomAvailabilityRequestDto roomAvailabilityRequestDto) {
        Optional<Room> availableRoom = roomRepository.findOneAvailableRoom(
                roomAvailabilityRequestDto.getNumberOfAdults(),
                roomAvailabilityRequestDto.getNumberOfChildren(),
                roomAvailabilityRequestDto.getCheckInDate(),
                roomAvailabilityRequestDto.getCheckOutDate()
        );

        return availableRoom.map(roomMapper::toRoomAvailabilityResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("No available room found for the specified criteria"));
    }

    public RoomResponseDto add(RoomRequestDto requestedRoom) {
        boolean roomExists = roomRepository.existsByRoomNumber(requestedRoom.getRoomNumber());

        if (roomExists) {
            throw new BusinessValidationException("Room number " + requestedRoom.getRoomNumber() + " already exists.");
        }

        Room roomEntity = roomMapper.toEntity(requestedRoom);
        Room savedRoom = roomRepository.save(roomEntity);

        return roomMapper.toResponseDto(savedRoom);
    }

    public RoomResponseDto update(Long roomId, RoomRequestDto requestedRoom) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + roomId + " not found."));

        room.setRoomNumber(requestedRoom.getRoomNumber());
        room.setRoomPrice(requestedRoom.getRoomPrice());
        room.setAdultCapacity(requestedRoom.getAdultCapacity());
        room.setChildrenCapacity(requestedRoom.getChildrenCapacity());

        Room savedRoom = roomRepository.save(room);

        return roomMapper.toResponseDto(savedRoom);
    }

    @Transactional
    public void removeById(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new ResourceNotFoundException("Room with ID " + roomId + " not found.");
        }

        if (roomAmenityRepository.existsByRoomId(roomId)) {
            roomAmenityRepository.deleteAllByRoomId(roomId);
        }

        roomRepository.deleteById(roomId);
    }

    public List<RoomAmenityResponseDto> getAmenities(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + roomId + " not found."));

        List<RoomAmenity> roomAmenities = roomAmenityRepository.findByRoomId(roomId);
        return roomAmenities.stream()
                .map(roomAmenityMapper::toResponseDto)
                .toList();
    }

    public RoomAmenityResponseDto addAmenity(Long roomId, RoomAmenityRequestDto requestDto) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + roomId + " not found."));

        AmenityType amenityType = amenityTypeRepository.findById(requestDto.getAmenityTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Amenity type with ID " + requestDto.getAmenityTypeId() + " not found."));

        RoomAmenity roomAmenity = roomAmenityMapper.toEntity(requestDto);
        roomAmenity.setRoom(room);
        roomAmenity.setAmenityType(amenityType);

        return roomAmenityMapper.toResponseDto(roomAmenityRepository.save(roomAmenity));
    }

    public void removeAmenity(Long roomId, Long roomAmenityId) {
        if (!roomRepository.existsById(roomId)) {
            throw new ResourceNotFoundException("Room with ID " + roomId + " not found.");
        }

        if (!roomAmenityRepository.existsById(roomAmenityId)) {
            throw new ResourceNotFoundException("Room amenity with ID " + roomId + " not found.");
        }

        roomAmenityRepository.deleteById(roomAmenityId);
    }
}
