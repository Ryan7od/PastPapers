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
import java.util.List;
import java.util.stream.Stream;

import static bookings.DateUtils.asDate;

// Controller
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

    BookingsEngine engine = new BookingsEngine(new DefaultBookingGenerator());
    List<Booking> bookings = engine.getBookings(customerId, asDate(searchStartDate), asDate(searchEndDate));

    resp.setContentType("application/json");
    resp.setStatus(HttpServletResponse.SC_OK);

    String json = gson.toJson(bookings);
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