package Q1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class PhoneCallTest {

  @Test
  public void exampleOfHowToUsePhoneCall() throws Exception {

    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    call.start();

    waitForSeconds(5);

    call.end();
    call.charge();
  }

  @Test
  public void chargesPeakCallCorrectly() throws Exception {
    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    LocalTime startTime = LocalTime.of(9, 15);
    LocalTime endTime = LocalTime.of(9, 17);

    Assert.assertTrue(startTime.isAfter(call.getPeakStartTime()));
    Assert.assertTrue(endTime.isAfter(startTime));
    Assert.assertTrue(endTime.isBefore(call.getPeakEndTime()));

    call.start(startTime);
    call.end(endTime);
    call.charge();

    Assert.assertEquals(call.getTotalCharge(), call.getPeakRate() * (MINUTES.between(startTime, endTime) + 1));
  }

  @Test
  public void chargesStartPeakEndOffPeakCallCorrectly() throws Exception {
    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    LocalTime startTime = LocalTime.of(17, 58);
    LocalTime endTime = LocalTime.of(18, 07);

    Assert.assertTrue(startTime.isAfter(call.getPeakStartTime()));
    Assert.assertTrue(endTime.isAfter(startTime));
    Assert.assertTrue(endTime.isAfter(call.getPeakEndTime()));

    call.start(startTime);
    call.end(endTime);
    call.charge();

    Assert.assertEquals(call.getTotalCharge(), call.getPeakRate() * (MINUTES.between(startTime, endTime) + 1));
  }

  @Test
  public void chargesStartOffPeakEndPeakCallCorrectly() throws Exception {
    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    LocalTime startTime = LocalTime.of(8, 58);
    LocalTime endTime = LocalTime.of(9, 07);

    Assert.assertTrue(startTime.isBefore(call.getPeakStartTime()));
    Assert.assertTrue(endTime.isAfter(startTime));
    Assert.assertTrue(endTime.isBefore(call.getPeakEndTime()));

    call.start(startTime);
    call.end(endTime);
    call.charge();

    Assert.assertEquals(call.getTotalCharge(), call.getPeakRate() * (MINUTES.between(startTime, endTime) + 1));
  }

  @Test
  public void chargesOffPeakCallCorrectly() throws Exception {
    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    LocalTime startTime = LocalTime.of(18, 15);
    LocalTime endTime = LocalTime.of(18, 17);

    Assert.assertTrue(startTime.isAfter(call.getPeakEndTime()));
    Assert.assertTrue(endTime.isAfter(startTime));
    Assert.assertTrue(endTime.isBefore(LocalTime.MAX));

    call.start(startTime);
    call.end(endTime);
    call.charge();

    Assert.assertEquals(call.getTotalCharge(), call.getOffPeakRate() * (MINUTES.between(startTime, endTime) + 1));
  }

  private void waitForSeconds(int n) throws Exception {
    Thread.sleep(n * 1000);
  }

}