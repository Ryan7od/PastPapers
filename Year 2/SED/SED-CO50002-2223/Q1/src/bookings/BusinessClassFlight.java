package bookings;

import availability.SeatManager;
import flights.*;
import java.time.LocalDate;
import java.util.List;

public class BusinessClassFlight extends FlightTemplate {
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
}