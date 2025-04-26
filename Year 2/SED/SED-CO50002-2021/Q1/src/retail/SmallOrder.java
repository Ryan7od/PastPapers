package retail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public class SmallOrder {

  private static final BigDecimal GIFT_WRAP_CHARGE = new BigDecimal(3);

  private final List<Product> items;
  private final CreditCardDetails creditCardDetails;
  private final Address billingAddress;
  private final Address shippingAddress;
  private final Courier courier;
  private final boolean giftWrap;

  public SmallOrder(
      List<Product> items,
      CreditCardDetails creditCardDetails,
      Address billingAddress,
      Address shippingAddress,
      Courier courier,
      boolean giftWrap) {
    this.items = Collections.unmodifiableList(items);
    this.creditCardDetails = creditCardDetails;
    this.billingAddress = billingAddress;
    this.shippingAddress = shippingAddress;
    this.courier = courier;
    this.giftWrap = giftWrap;
  }

  public void process() {

    BigDecimal total = new BigDecimal(0);

    for (Product item : items) {
      total = total.add(item.unitPrice());
    }

    total = total.add(courier.deliveryCharge());

    if (giftWrap) {
      total = total.add(GIFT_WRAP_CHARGE);
    }

    CreditCardProcessor.getInstance().charge(round(total), creditCardDetails, billingAddress);

    if (giftWrap) {
      courier.send(new GiftBox(items), shippingAddress);
    } else {
      courier.send(new Parcel(items), shippingAddress);
    }
  }

  private BigDecimal round(BigDecimal amount) {
    return amount.setScale(2, RoundingMode.CEILING);
  }
}
