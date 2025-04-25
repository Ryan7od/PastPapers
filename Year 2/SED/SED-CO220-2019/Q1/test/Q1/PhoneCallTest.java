package Q1;

import org.junit.Test;

public class PhoneCallTest {

  @Test
  public void exampleOfHowToUsePhoneCall() throws Exception {

    PhoneCall call = new PhoneCall("+447770123456", "+4479341554433");

    call.start();

    waitForSeconds(150);

    call.end();
    call.charge();
  }

  private void waitForSeconds(int n) throws Exception {
    Thread.sleep(n * 1000);
  }

}