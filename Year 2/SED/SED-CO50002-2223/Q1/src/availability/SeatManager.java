package availability;

import flights.FlightNumber;
import flights.Seat;
import flights.ServiceLevel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SeatManager {

  private static final SeatManager INSTANCE = new SeatManager();

  private final List<Seat> availableSeats;

  private SeatManager() {

    // set up an initial set of available seats (not a real implementation - just for the exam)

    List<Seat> seats = new ArrayList<>();
    for (int row = 1; row < 6; row++) {
      for (String s : List.of("A", "C", "D", "F")) {
        seats.add(new Seat(row + s, ServiceLevel.FIRST));
      }
    }
    for (int row = 7; row < 14; row++) {
      for (String s : List.of("A", "C", "D", "F")) {
        seats.add(new Seat(row + s, ServiceLevel.BUSINESS));
      }
    }
    for (int row = 15; row < 32; row++) {
      for (String s : List.of("A", "B", "C", "D", "E", "F")) {
        seats.add(new Seat(row + s, ServiceLevel.ECONOMY));
      }
    }

    availableSeats = seats.stream().filter(s -> Math.random() > 0.5).toList();
  }

  public static SeatManager getInstance() {
    return INSTANCE;
  }

  public List<Seat> getAvailableSeats(FlightNumber flightNumber, LocalDate date) {
    return availableSeats;
  }
}
