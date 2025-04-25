package bookings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
  public static LocalDate asDate(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return LocalDate.parse(dateString, formatter);
  }
}
