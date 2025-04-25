package bookings;

import availability.SeatManager;
import flights.*;
import java.time.LocalDate;
import java.util.List;

public class BusinessClassFlight {

  private static final int PENCE_PER_MILE = 35;
  private static final int STANDARD_FEE_PENCE = 8000;

  public BusinessClassFlight(
      FlightNumber flightNumber, LocalDate date, Airport origin, Airport destination) {
    this.flightNumber = flightNumber;
    this.date = date;
    this.origin = origin;
    this.destination = destination;
  }

  private final FlightNumber flightNumber;
  private final LocalDate date;
  private final Airport origin;
  private final Airport destination;

  public List<Seat> seatingOptions(FrequentFlyerStatus status) {
    if (date.isBefore(LocalDate.now())) {
      throw new BookingException("Flight is in the past");
    }
    List<Seat> availableSeats = SeatManager.getInstance().getAvailableSeats(flightNumber, date);
    if (status == FrequentFlyerStatus.ELITE) {
      // Elite status customers can choose any seat, even in first class.
      return availableSeats;
    }
    return availableSeats.stream().filter(s -> s.cabin() == ServiceLevel.BUSINESS).toList();
  }

  public int calculateFare() {
    return origin.distanceTo(destination) * PENCE_PER_MILE + STANDARD_FEE_PENCE;
  }

  @Override
  public String toString() {
    return "Flight "
        + flightNumber
        + " ("
        + date
        + ") from "
        + origin
        + " to "
        + destination
        + " ("
        + ServiceLevel.BUSINESS
        + ")";
  }
}
