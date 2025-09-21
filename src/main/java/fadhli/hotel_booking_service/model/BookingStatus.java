package fadhli.hotel_booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookingStatus {
    CHECKED_IN("Checked-in"),
    CHECKED_OUT("Checked-out"),
    CANCELED("Canceled"),
    BOOKED("Booked");

    private final String label;
}
