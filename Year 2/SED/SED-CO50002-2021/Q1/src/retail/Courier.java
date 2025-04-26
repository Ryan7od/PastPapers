package retail;

import java.math.BigDecimal;

public interface Courier {
  void send(Parcel shipment, Address shippingAddress);

  BigDecimal deliveryCharge();
}
