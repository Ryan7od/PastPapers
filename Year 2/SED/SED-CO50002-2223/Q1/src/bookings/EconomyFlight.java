package bookings;

import availability.SeatManager;
import flights.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class EconomyFlight {

  private static final int PENCE_PER_MILE = 15;
  private static final int STANDARD_FEE_PENCE = 4000;

  public EconomyFlight(
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
    List<Seat> allAvailableEconomySeats =
        availableSeats.stream().filter(s -> s.cabin() == ServiceLevel.ECONOMY).toList();
    return switch (status) {
      case BASIC -> pickOneAtRandomFrom(allAvailableEconomySeats);
      case SILVER, ELITE -> allAvailableEconomySeats;
    };
  }

  private List<Seat> pickOneAtRandomFrom(List<Seat> allAvailableEconomySeats) {
    int randomPositionInList = (int) (Math.random() * (allAvailableEconomySeats.size() - 1));
    return Collections.singletonList(allAvailableEconomySeats.get(randomPositionInList));
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
        + ServiceLevel.ECONOMY
        + ")";
  }
}
