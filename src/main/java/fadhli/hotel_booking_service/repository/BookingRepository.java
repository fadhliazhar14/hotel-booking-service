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

    @Query("SELECT b FROM Booking b LEFT JOIN b.room r WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(CONCAT(b.firstName, ' ', b.lastName)) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(b.id AS string) LIKE CONCAT('%', :search, '%') OR " +
            "CAST(b.room.id AS string) LIKE CONCAT('%', :search, '%')) AND " +
            "(UPPER(CAST(b.bookingStatus AS string)) = :status OR :status IS NULL)")
    Page<Booking> findAllWithPagination(@Param("search") String search, @Param("status") String status, Pageable pageable);
}