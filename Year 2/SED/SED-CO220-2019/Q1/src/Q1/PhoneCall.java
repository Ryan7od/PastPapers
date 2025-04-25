package Q1;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalTime;

public class PhoneCall {

  private static final long PEAK_RATE = 25;
  private static final long OFF_PEAK_RATE = 10;

  private final String caller;
  private final String callee;

  private LocalTime startTime;
  private LocalTime endTime;

  public PhoneCall(String caller, String callee) {
    this.caller = caller;
    this.callee = callee;
  }

  public void start() {
    startTime = LocalTime.now();
  }

  public void end() {
    endTime = LocalTime.now();
  }

  public void charge() {
    BillingSystem.getInstance().addBillItem(caller, callee, priceInPence());
  }

  private long priceInPence() {
    if (startTime.isAfter(LocalTime.of(9, 00)) && endTime.isBefore(LocalTime.of(18, 00))) {
      return duration() * PEAK_RATE;
    } else {
      return duration() * OFF_PEAK_RATE;
    }
  }

  private long duration() {
    return MINUTES.between(startTime, endTime) + 1;
  }

}
