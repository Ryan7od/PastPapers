package Q1;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalTime;

public class PhoneCall {

  private static final long PEAK_RATE = 25;
  private static final long OFF_PEAK_RATE = 10;
  private final LocalTime PEAK_START_TIME = LocalTime.of(9, 00);
  private final LocalTime PEAK_END_TIME = LocalTime.of(18, 00);

  private final String caller;
  private final String callee;

  private LocalTime startTime;
  private LocalTime endTime;

  private long totalCharge = 0;



  public PhoneCall(String caller, String callee) {
    this.caller = caller;
    this.callee = callee;
  }

  public void start() {
    startTime = LocalTime.now();
  }
  public void start(LocalTime time) {
    startTime = time;
  }

  public void end() {
    endTime = LocalTime.now();
  }
  public void end(LocalTime time) {
    endTime = time;
  }

  public void charge() {
    long charge = priceInPence();
    BillingSystem.getInstance().addBillItem(caller, callee, charge);
    totalCharge += charge;
  }
  public long getTotalCharge() { return totalCharge; }

  public long getPeakRate()    { return PEAK_RATE; }
  public long getOffPeakRate() { return OFF_PEAK_RATE; }

  public LocalTime getPeakStartTime() { return PEAK_START_TIME; }
  public LocalTime getPeakEndTime() { return PEAK_END_TIME; }

  private long priceInPence() {
    if (startTime.isAfter(PEAK_START_TIME) && startTime.isBefore(PEAK_END_TIME)
     || endTime.isBefore(PEAK_END_TIME) && endTime.isAfter(PEAK_START_TIME)) {
      return duration() * PEAK_RATE;
    } else {
      return duration() * OFF_PEAK_RATE;
    }
  }

  private long duration() {
    return MINUTES.between(startTime, endTime) + 1;
  }

}
