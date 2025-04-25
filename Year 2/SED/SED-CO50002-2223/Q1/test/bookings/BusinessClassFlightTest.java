package bookings;

import flights.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BusinessClassFlightTest {
  List<Seat> FirstSeats = new ArrayList<>();
  List<Seat> BusinessSeats = new ArrayList<>();
  List<Seat> EconomySeats = new ArrayList<>();

  private void setupSeatLists() {
    List<Seat> seats = new ArrayList<>();
    for (int row = 1; row < 6; row++) {
      for (String s : List.of("A", "C", "D", "F")) {
        FirstSeats.add(new Seat(row + s, ServiceLevel.FIRST));
      }
    }

    for (int row = 7; row < 14; row++) {
      for (String s : List.of("A", "C", "D", "F")) {
        BusinessSeats.add(new Seat(row + s, ServiceLevel.BUSINESS));
      }
    }

    for (int row = 15; row < 32; row++) {
      for (String s : List.of("A", "B", "C", "D", "E", "F")) {
        EconomySeats.add(new Seat(row + s, ServiceLevel.ECONOMY));
      }
    }
  }

  private BusinessClassFlight setupFlight() {
    BusinessClassFlight flight =
            new BusinessClassFlight(
                    FlightNumber.of("BA773"), LocalDate.of(2025, 6, 30), Airport.LHR, Airport.JFK, new DeterministicSeatManager()
            );
    setupSeatLists();
    setupSeatLists();
    setupSeatLists();

    return flight;
  }


  @Test
  public void EliteCanChooseAny() {
    BusinessClassFlight flight = setupFlight();
    List<Seat> availableSeats = flight.seatingOptions(FrequentFlyerStatus.ELITE);
    List<String> availableSeatsString = availableSeats.stream().map(Seat::toString).toList();

    for (Seat seat : FirstSeats) {
      Assert.assertTrue(availableSeatsString.contains(seat.toString()));
    }
    for (Seat seat : BusinessSeats) {
      Assert.assertTrue(availableSeatsString.contains(seat.toString()));
    }
    for (Seat seat : EconomySeats) {
      Assert.assertTrue(availableSeatsString.contains(seat.toString()));
    }
  }

  @Test
  public void SilverCanOnlyChooseBusiness() {
    BusinessClassFlight flight = setupFlight();
    List<Seat> availableSeats = flight.seatingOptions(FrequentFlyerStatus.SILVER);
    List<String> availableSeatsString = availableSeats.stream().map(Seat::toString).toList();

    for (Seat seat : FirstSeats) {
      Assert.assertFalse(availableSeatsString.contains(seat.toString()));
    }
    for (Seat seat : BusinessSeats) {
      Assert.assertTrue(availableSeatsString.contains(seat.toString()));
    }
    for (Seat seat : EconomySeats) {
      Assert.assertFalse(availableSeatsString.contains(seat.toString()));
    }
  }

  @Test
  public void BasicCanOnlyChooseBusiness() {
    BusinessClassFlight flight = setupFlight();
    List<Seat> availableSeats = flight.seatingOptions(FrequentFlyerStatus.BASIC);
    List<String> availableSeatsString = availableSeats.stream().map(Seat::toString).toList();

    for (Seat seat : FirstSeats) {
      Assert.assertFalse(availableSeatsString.contains(seat.toString()));
    }
    for (Seat seat : BusinessSeats) {
      Assert.assertTrue(availableSeatsString.contains(seat.toString()));
    }
    for (Seat seat : EconomySeats) {
      Assert.assertFalse(availableSeatsString.contains(seat.toString()));
    }
  }
}
