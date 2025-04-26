package retail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    public static void main(String[] args) {
        System.out.println((10.0 + 12.0 + 8.5 + 18.0 + 30 + 1) * 0.9);
    }

    private List<Product> items = new ArrayList<>();
    private CreditCardDetails creditCardDetails;
    private PaymentMethod paymentMethod;
    private Address billingAddress;
    private Address shippingAddress = null;
    private Courier courier;
    private boolean giftWrap = false;
    private BigDecimal discount = BigDecimal.ZERO;

    public OrderBuilder addItems(List<Product> items) {
        this.items.addAll(items);
        return this;
    }

    public OrderBuilder addItem(Product item) {
        this.items.add(item);
        return this;
    }

    public OrderBuilder setCreditCardDetails(CreditCardDetails creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
        return this;
    }

    public OrderBuilder setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderBuilder setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public OrderBuilder setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public OrderBuilder setCourier(Courier courier) {
        this.courier = courier;
        return this;
    }

    public OrderBuilder withGiftWrapping() {
        this.giftWrap = true;
        return this;
    }

    public OrderBuilder setBulkDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public Order createOrder() {
        if (shippingAddress == null) {
            shippingAddress = billingAddress;
        }

        if (items.size() <= 3) { // Small Order
            if (!discount.equals(BigDecimal.ZERO)) {
                throw new IllegalArgumentException("Can't apply a discount on a small order");
            }

            return new SmallOrder(items, creditCardDetails, paymentMethod, billingAddress, shippingAddress, courier, giftWrap);
        } else { // Bulk Order
            if (giftWrap) {
                throw new IllegalArgumentException("Can't gift wrap a bulk order");
            }

            return new BulkOrder(items, creditCardDetails, paymentMethod, billingAddress, shippingAddress, courier, discount);
        }
    }
}