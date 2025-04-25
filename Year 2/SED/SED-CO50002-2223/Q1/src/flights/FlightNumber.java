package flights;

public class FlightNumber {

  private final String code;

  private FlightNumber(String code) {
    this.code = code;
  }

  public static FlightNumber of(String code) {
    return new FlightNumber(code);
  }

  @Override
  public String toString() {
    return code;
  }
}
