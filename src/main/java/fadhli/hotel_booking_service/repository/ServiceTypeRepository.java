package fadhli.hotel_booking_service.repository;

import fadhli.hotel_booking_service.entity.ServiceType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    List<ServiceType> findByIsActiveTrue(Sort sort);

    boolean existsByName(String name);
}
