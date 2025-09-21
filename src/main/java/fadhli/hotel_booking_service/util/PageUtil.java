package fadhli.hotel_booking_service.util;

import fadhli.hotel_booking_service.dto.common.PageRequestDto;
import fadhli.hotel_booking_service.dto.common.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageUtil {
    public static Pageable createPageable(PageRequestDto pageRequestDTO) {
        Sort.Direction direction = "desc".equalsIgnoreCase(pageRequestDTO.getDirection())
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, pageRequestDTO.getSort());

        return PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), sort);
    }

    public static <T> PageResponseDto<T> createPageResponse(Page<T> page, PageRequestDto pageRequestDTO) {
        return PageResponseDto.of(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty(),
                pageRequestDTO.getSort(),
                pageRequestDTO.getDirection()
        );
    }

    public static <T> PageResponseDto<T> createPageResponse(List<T> content, Pageable pageable, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        boolean isFirst = pageable.getPageNumber() == 0;
        boolean isLast = pageable.getPageNumber() >= totalPages - 1;
        boolean isEmpty = content.isEmpty();

        String sort = pageable.getSort().isSorted()
                ? pageable.getSort().iterator().next().getProperty()
                : "id";
        String direction = pageable.getSort().isSorted()
                ? pageable.getSort().iterator().next().getDirection().name().toLowerCase()
                : "asc";

        return PageResponseDto.of(
                content,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                totalElements,
                totalPages,
                isFirst,
                isLast,
                isEmpty,
                sort,
                direction
        );
    }
}