package bookings;

import java.time.LocalDate;

public record Booking(String customerId, String origin, String destination, LocalDate date) {}
