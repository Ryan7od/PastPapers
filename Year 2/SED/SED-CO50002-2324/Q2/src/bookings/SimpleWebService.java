package bookings;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class SimpleWebService {

  public static void main(String[] args) throws Exception {

    Server server = new Server(8888); // Create web server on port 8888

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    // Map BookingsApiServlet to the path "/bookings"
    context.addServlet(
        new ServletHolder(new BookingsApiServlet()), "/bookings");

    // Start the server
    server.start();
    server.join();
  }

}
