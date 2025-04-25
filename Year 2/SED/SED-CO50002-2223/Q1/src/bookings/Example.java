package bookings;

import flights.Airport;
import flights.FlightNumber;
import flights.FrequentFlyerStatus;
import flights.Seat;
import java.time.LocalDate;
import java.util.List;

public class Example {

  public static void main(String[] args) {

    EconomyFlight flight =
        new EconomyFlight(
            FlightNumber.of("BA175"), LocalDate.of(2023, 6, 30), Airport.LHR, Airport.JFK);

    List<Seat> availableSeats = flight.seatingOptions(FrequentFlyerStatus.SILVER);

    for (Seat availableSeat : availableSeats) {
      System.out.println(availableSeat);
    }

    int fareInPence = flight.calculateFare();

    System.out.println(flight + ": £" + fareInPence / 100);
  }
}
