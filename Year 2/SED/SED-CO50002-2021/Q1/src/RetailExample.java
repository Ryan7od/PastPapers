import retail.*;

import java.math.BigDecimal;
import java.util.List;

public class RetailExample {

  public static void main(String[] args) {

    Courier royalMail = new RoyalMail();
    Courier fedex = new Fedex();

    BulkOrder bigOrder =
        new BulkOrder(
            List.of(
                new Product("One Book", new BigDecimal("10.00")),
                new Product("One Book", new BigDecimal("10.00")),
                new Product("One Book", new BigDecimal("10.00")),
                new Product("One Book", new BigDecimal("10.00")),
                new Product("One Book", new BigDecimal("10.00")),
                new Product("One Book", new BigDecimal("10.00"))),
            new CreditCardDetails("1234123412341234", 111),
            new Address("180 Queens Gate, London, SW7 2AZ"),
            new Address("180 Queens Gate, London, SW7 2AZ"),
            fedex,
            BigDecimal.ZERO);

    bigOrder.process();

    SmallOrder smallOrder =
        new SmallOrder(
            List.of(
                new Product("One Book", new BigDecimal("10.00"))
            ),
            new CreditCardDetails("1234123412341234", 111),
            new Address("180 Queens Gate, London, SW7 2AZ"),
            new Address("180 Queens Gate, London, SW7 2AZ"),
            royalMail,
            false);

    smallOrder.process();
  }
}

class RoyalMail implements Courier {
  @Override
  public void send(Parcel shipment, Address shippingAddress) {
    System.out.println("Royal mail will your parcel deliver to: " + shippingAddress);
  }

  @Override
  public BigDecimal deliveryCharge() {
    return new BigDecimal(3);
  }
}

class Fedex implements Courier {
  @Override
  public void send(Parcel shipment, Address shippingAddress) {
    System.out.println("Fedex will deliver your parcel to: " + shippingAddress);
  }

  @Override
  public BigDecimal deliveryCharge() {
    return new BigDecimal(8);
  }
}
