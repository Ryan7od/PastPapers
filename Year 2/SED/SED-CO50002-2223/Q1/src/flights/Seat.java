package flights;

public class Seat {

  private ServiceLevel cabin;
  private String seatNumber;

  public Seat(String seatNumber, ServiceLevel cabin) {
    this.seatNumber = seatNumber;
    this.cabin = cabin;
  }

  public ServiceLevel cabin() {
    return cabin;
  }

  @Override
  public String toString() {
    return seatNumber + "(" + cabin + ")";
  }
}
