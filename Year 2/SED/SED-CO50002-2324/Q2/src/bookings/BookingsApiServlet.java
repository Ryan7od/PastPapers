package bookings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static bookings.DateUtils.asDate;

public class BookingsApiServlet extends HttpServlet {

  // JSON serialization machinery

  // We need a custom GSON Serializer for LocalDate
  private final JsonSerializer<LocalDate> serializer =
      (src, type, context) ->
          new com.google.gson.JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));

  private final Gson gson =
      new GsonBuilder().registerTypeAdapter(LocalDate.class, serializer).create();


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String customerId = req.getParameter("customerId");
    String searchStartDate = req.getParameter("startDate");
    String searchEndDate = req.getParameter("endDate");

    // log the parameters
    System.out.println("customerId: " + customerId);
    System.out.println("startDate: " + searchStartDate);
    System.out.println("endDate: " + searchEndDate);

    Stream<Booking> bookings = BookingsDatabase.getInstance().getBookings().stream();

    if (customerId != null) {

      bookings = bookings.filter(booking -> booking.customerId().equals(customerId));
    }

    if (searchStartDate != null) {
      LocalDate startDate = asDate(searchStartDate);
      bookings = bookings.filter(booking -> booking.date().isAfter(startDate));
    }

    if (searchEndDate != null) {
      LocalDate endDate = asDate(searchEndDate);
      bookings = bookings.filter(booking -> booking.date().isBefore(endDate));
    }

    resp.setContentType("application/json");
    resp.setStatus(HttpServletResponse.SC_OK);

    String json = gson.toJson(bookings.toList());
    resp.getWriter().println(json);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Not implemented fully in this example, but this method would be used to add
    // a new booking to the booking system.

    // The booking would be read from the request body and added to the database.
  }

}