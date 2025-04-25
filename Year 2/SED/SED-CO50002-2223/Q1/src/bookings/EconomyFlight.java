package bookings;

import availability.SeatManager;
import flights.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class EconomyFlight extends FlightTemplate {
  private static final int PENCE_PER_MILE = 15;
  private static final int STANDARD_FEE_PENCE = 4000;

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

  @Override
  protected int getPerMileCost() {
    return PENCE_PER_MILE;
  }

  @Override
  protected int getStandardFee() {
    return STANDARD_FEE_PENCE;
  }
}
