package bookings;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

// Model
public class BookingsEngine {
    private BookingGenerator bookingGen;

    public BookingsEngine(BookingGenerator bookingGen) {
        this.bookingGen = bookingGen;
    }

    public List<Booking> getBookings(String customerId, LocalDate startDate, LocalDate endDate) {
        Stream<Booking> bookings = BookingsDatabase.getInstance().getBookings().stream();

        if (customerId != null) {

            bookings = bookings.filter(booking -> booking.customerId().equals(customerId));
        }

        if (startDate != null) {
            bookings = bookings.filter(booking -> booking.date().isAfter(startDate));
        }

        if (endDate != null) {
            bookings = bookings.filter(booking -> booking.date().isBefore(endDate));
        }

        return bookings.toList();
    }
}
