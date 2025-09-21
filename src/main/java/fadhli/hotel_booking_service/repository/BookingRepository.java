package fadhli.hotel_booking_service.repository;

import fadhli.hotel_booking_service.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(CONCAT(b.firstName, ' ', b.lastName)) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(b.id AS string) LIKE CONCAT('%', :search, '%') OR " +
            "CAST(b.room.id AS string) LIKE CONCAT('%', :search, '%') OR " +
            "LOWER(CAST(b.bookingStatus AS string)) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Booking> findAllWithPagination(@Param("search") String search, Pageable pageable);
}
