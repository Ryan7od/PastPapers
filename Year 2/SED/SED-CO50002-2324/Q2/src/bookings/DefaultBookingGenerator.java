package bookings;

import java.util.Collection;
import java.util.List;

public class DefaultBookingGenerator implements BookingGenerator {
    @Override
    public Collection<Booking> getBookings() {
        return BookingsDatabase.getInstance().getBookings();
    }
}
