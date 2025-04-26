package retail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

abstract public class Order {
    protected final List<Product> items;
    protected final CreditCardDetails creditCardDetails;
    protected final PaymentMethod paymentMethod;
    protected final Address billingAddress;
    protected final Address shippingAddress;
    protected final Courier courier;

    public Order(
            List<Product> items,
            CreditCardDetails creditCardDetails,
            PaymentMethod paymentMethod,
            Address billingAddress,
            Address shippingAddress,
            Courier courier) {
        this.items = Collections.unmodifiableList(items);
        this.creditCardDetails = creditCardDetails;
        this.paymentMethod = paymentMethod;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.courier = courier;
    }

    public void process() {

        BigDecimal total = new BigDecimal(0);

        for (Product item : items) {
            total = total.add(item.unitPrice());
        }

        total = addTotalExtras(total);

        paymentMethod.charge(round(total), creditCardDetails, billingAddress);

        send();
    }

    protected abstract void send();
    protected abstract BigDecimal addTotalExtras(BigDecimal total);

    protected BigDecimal round(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.CEILING);
    }
}
