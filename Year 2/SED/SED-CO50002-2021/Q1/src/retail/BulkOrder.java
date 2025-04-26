package retail;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

public class BulkOrder extends Order {
  private final BigDecimal discount;

  public BulkOrder(
      List<Product> items,
      CreditCardDetails creditCardDetails,
      PaymentMethod paymentMethod,
      Address billingAddress,
      Address shippingAddress,
      Courier courier,
      BigDecimal discount) {
    super(items, creditCardDetails, paymentMethod, billingAddress, shippingAddress, courier);
    this.discount = discount;
  }

  @Override
  protected void send() {
    courier.send(new Parcel(items), shippingAddress);
  }

  @Override
  protected BigDecimal addTotalExtras(BigDecimal total) {
    if (items.size() > 10) {
      total = total.multiply(BigDecimal.valueOf(0.8));
    } else if (items.size() > 5) {
      total = total.multiply(BigDecimal.valueOf(0.9));
    }

    total = total.subtract(discount);

    return total;
  }
}
