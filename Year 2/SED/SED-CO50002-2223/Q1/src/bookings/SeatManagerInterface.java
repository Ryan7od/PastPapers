package bookings;

import availability.SeatManager;
import flights.FlightNumber;
import flights.Seat;

import java.time.LocalDate;
import java.util.List;

public interface SeatManagerInterface {
    public List<Seat> getAvailableSeats(FlightNumber flightNumber, LocalDate date);
}

