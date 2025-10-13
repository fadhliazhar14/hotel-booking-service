package fadhli.hotel_booking_service.repository;

import fadhli.hotel_booking_service.entity.Booking;
import fadhli.hotel_booking_service.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByRoomNumber(Integer roomNumber);

    @Query("SELECT r.roomNumber FROM Room r WHERE r.id = :roomId")
    Integer findRoomNumberById(@Param("roomId") Long roomId);

    @Query("SELECT r FROM Room r WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "CAST(r.roomNumber AS string) LIKE CONCAT('%', :search, '%'))")
    Page<Room> findAllWithPagination(@Param("search") String search, Pageable pageable);

    @Query(value = """
            SELECT r.*
            FROM rooms r
            WHERE r.adult_capacity >= :numberOfAdults
            AND (r.adult_capacity + r.children_capacity) >= (:numberOfAdults + :numberOfChildren)
            AND NOT EXISTS (
                SELECT 1
                FROM bookings b
                WHERE b.room_id = r.id
                AND b.booking_status IN ('BOOKED', 'CHECKED_IN')
                AND NOT (
                    b.checked_out_date < :checkInDate
                    OR b.checked_in_date > :checkOutDate
                )
            )
            ORDER BY r.room_price ASC
            LIMIT 1
            """, nativeQuery = true)
    Optional<Room> findOneAvailableRoom(
            @Param("numberOfAdults") int numberOfAdults,
            @Param("numberOfChildren") int numberOfChildren,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );
}
