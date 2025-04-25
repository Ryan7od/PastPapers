package bookings;

import availability.SeatManager;
import flights.*;

import java.time.LocalDate;
import java.util.List;

abstract public class FlightTemplate {

    public FlightTemplate (
            FlightNumber flightNumber, LocalDate date, Airport origin, Airport destination, SeatManagerInterface seatManager) {
        this.flightNumber = flightNumber;
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.seatManager = seatManager;
    }

    protected final FlightNumber flightNumber;
    protected final LocalDate date;
    protected final Airport origin;
    protected final Airport destination;
    private final SeatManagerInterface seatManager;

    public List<Seat> seatingOptions(FrequentFlyerStatus status) {
        if (date.isBefore(LocalDate.now())) {
            throw new BookingException("Flight is in the past");
        }
        List<Seat> availableSeats = seatManager.getAvailableSeats(flightNumber, date);
        return chooseSeat(availableSeats, status);
    }

    abstract List<Seat> chooseSeat(List<Seat> availableSeats, FrequentFlyerStatus status);

    public int calculateFare() {
        return origin.distanceTo(destination) * getPerMileCost() + getStandardFee();
    }

    protected abstract int getPerMileCost();
    protected abstract int getStandardFee();

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
