package fadhli.hotel_booking_service.repository;

import fadhli.hotel_booking_service.entity.AmenityType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenityTypeRepository extends JpaRepository<AmenityType, Long> {
    List<AmenityType> findByIsActiveTrue(Sort sort);

    boolean existsByName(String name);
}
