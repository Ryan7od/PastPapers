package bookings;

import availability.SeatManager;
import flights.*;
import java.time.LocalDate;
import java.util.List;

public class BusinessClassFlight extends FlightTemplate {
  protected static final int PENCE_PER_MILE = 35;
  protected static final int STANDARD_FEE_PENCE = 8000;

  public BusinessClassFlight(
      FlightNumber flightNumber, LocalDate date, Airport origin, Airport destination, SeatManagerInterface seatManager) {
    super(flightNumber, date, origin, destination, seatManager);
  }

  @Override
  List<Seat> chooseSeat(List<Seat> availableSeats, FrequentFlyerStatus status) {
    if (status == FrequentFlyerStatus.ELITE) {
      // Elite status customers can choose any seat, even in first class.
      return availableSeats;
    }
    return availableSeats.stream().filter(s -> s.cabin() == ServiceLevel.BUSINESS).toList();
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