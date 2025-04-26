package retail;

public class CreditCardDetails {

  private final String number;
  private final int securityCode;

  public CreditCardDetails(String number, int securityCode) {
    this.number = number;
    this.securityCode = securityCode;
  }

  @Override
  public String toString() {
    return number;
  }
}
