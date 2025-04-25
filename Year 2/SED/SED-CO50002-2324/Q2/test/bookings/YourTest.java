package bookings;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static bookings.DateUtils.asDate;

public class YourTest {
  @Test
  public void correctlyFiltersCustomerId() {
    BookingsEngine engine = new BookingsEngine(new CustomBookingGenerator());

    List<Booking> expectedBookings = new ArrayList<>();
    expectedBookings.add(new Booking("123", "LHR", "MUC", asDate("15-05-2024")));
    expectedBookings.add(new Booking("123", "LHR", "JFK", asDate("21-06-2024")));
    expectedBookings.add(new Booking("123", "JFK", "LHR", asDate("28-06-2024")));

    List<Booking> actualBookings = engine.getBookings("123", null, null);

    Assert.assertEquals(expectedBookings, actualBookings);
  }

  @Test
  public void correctlyFiltersStartDate() {
    BookingsEngine engine = new BookingsEngine(new CustomBookingGenerator());

    List<Booking> expectedBookings = new ArrayList<>();
    expectedBookings.add(new Booking("321", "SFO", "JFK", asDate("01-10-2024")));
    expectedBookings.add(new Booking("222", "LGW", "EDI", asDate("01-08-2024")));

    List<Booking> actualBookings = engine.getBookings(null, LocalDate.of(2024, 07, 10), null);

    Assert.assertEquals(expectedBookings, actualBookings);
  }

  @Test
  public void correctlyFiltersEndDate() {
    BookingsEngine engine = new BookingsEngine(new CustomBookingGenerator());

    List<Booking> expectedBookings = new ArrayList<>();
    expectedBookings.add(new Booking("123", "LHR", "MUC", asDate("15-05-2024")));
    expectedBookings.add(new Booking("123", "LHR", "JFK", asDate("21-06-2024")));
    expectedBookings.add(new Booking("123", "JFK", "LHR", asDate("28-06-2024")));
    expectedBookings.add(new Booking("321", "LCY", "ZRH", asDate("11-06-2024")));

    List<Booking> actualBookings = engine.getBookings(null, null,  LocalDate.of(2024, 07, 10));

    Assert.assertEquals(expectedBookings, actualBookings);
  }

  static class CustomBookingGenerator implements BookingGenerator {

    @Override
    public Collection<Booking> getBookings() {
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
  }
}