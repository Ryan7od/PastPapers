package retail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public class SmallOrder extends Order {
  private static final BigDecimal GIFT_WRAP_CHARGE = new BigDecimal(3);
  private final boolean giftWrap;

  public SmallOrder(
      List<Product> items,
      CreditCardDetails creditCardDetails,
      PaymentMethod paymentMethod,
      Address billingAddress,
      Address shippingAddress,
      Courier courier,
      boolean giftWrap) {
    super(items, creditCardDetails, paymentMethod, billingAddress, shippingAddress, courier);
    this.giftWrap = giftWrap;
  }

  @Override
  protected void send() {
    if (giftWrap) {
      courier.send(new GiftBox(items), shippingAddress);
    } else {
      courier.send(new Parcel(items), shippingAddress);
    }
  }

  @Override
  protected BigDecimal addTotalExtras(BigDecimal total) {
    total = total.add(courier.deliveryCharge());

    if (giftWrap) {
      total = total.add(GIFT_WRAP_CHARGE);
    }

    return total;
  }
}
