package fadhli.hotel_booking_service.repository;

import fadhli.hotel_booking_service.entity.BookingService;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingServiceRepository extends JpaRepository<BookingService, Long> {
    @Query("SELECT bs FROM BookingService bs WHERE bs.booking.id = :bookingId")
    List<BookingService> findByBookingId(@NonNull Long bookingId);

    @Query("SELECT CASE WHEN COUNT(rs) > 0 THEN true ELSE false END FROM BookingService rs WHERE rs.booking.id = :bookingId")
    boolean existsByBookingId(@NonNull Long bookingId);

    @Modifying
    @Query("DELETE FROM BookingService bs WHERE bs.booking.id = :bookingId")
    void deleteAllByBookingId(@Param("bookingId") Long bookingId);
}
