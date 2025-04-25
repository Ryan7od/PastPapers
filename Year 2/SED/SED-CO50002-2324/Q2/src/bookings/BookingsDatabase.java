package bookings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static bookings.DateUtils.asDate;

public class BookingsDatabase {

  private static final BookingsDatabase instance = new BookingsDatabase();

  private final Collection<Booking> allBookings;

  private BookingsDatabase() {
    allBookings = allCurrentBookings();
  }

  public static BookingsDatabase getInstance() {
    return instance;
  }

  private List<Booking> allCurrentBookings() {

    List<Booking> bookings = new ArrayList<>();

    bookings.add(
        new Booking("123", "LHR", "MUC", asDate("15-05-2024")));

    bookings.add(
        new Booking("123", "LHR", "JFK", asDate("21-06-2024")));

    bookings.add(
        new Booking("123", "JFK", "LHR", asDate("28-06-2024")));

    bookings.add(
        new Booking("321", "SFO", "JFK", asDate("01-10-2024")));

    bookings.add(
        new Booking("321", "LCY", "ZRH", asDate("11-06-2024")));

    bookings.add(
        new Booking("222", "LGW", "EDI", asDate("01-08-2024")));

    return bookings;
  }

  public Collection<Booking> getBookings() {
    return Collections.unmodifiableCollection(allBookings);
  }

  public void addBooking(Booking booking) {
    allBookings.add(booking);
  }
}
