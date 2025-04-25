package bookings;

import availability.SeatManager;
import flights.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class EconomyFlight extends FlightTemplate {

  public EconomyFlight(
      FlightNumber flightNumber, LocalDate date, Airport origin, Airport destination, SeatManagerInterface seatManager) {
    super(flightNumber, date, origin, destination, seatManager);
  }

  @Override
  List<Seat> chooseSeat(List<Seat> availableSeats, FrequentFlyerStatus status) {
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
}
