package retail;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderTest {
    JUnitRuleMockery context = new JUnitRuleMockery();
    PaymentMethod paymentMethod = context.mock(PaymentMethod.class);
    CreditCardDetails creditCardDetails = new CreditCardDetails("123", 123);
    Address address = new Address("this address");


    @Test
    public void chargesOrdersCorrectly() {
        List<Product> items = new ArrayList<>();
        items.addAll(List.of(
                new Product("Item 1", new BigDecimal("10.0")),
                new Product("Item 2", new BigDecimal("12.0")),
                new Product("Item 3", new BigDecimal("8.5")),
                new Product("Item 4", new BigDecimal("18.0")),
                new Product("Item 5", new BigDecimal("30.0")),
                new Product("Item 6", new BigDecimal("1.0"))
        ));

        // Small order
        Order small1 = new OrderBuilder()
                .addItem(items.get(0))
                .addItem(items.get(1))
                .setCreditCardDetails(creditCardDetails)
                .setPaymentMethod(paymentMethod)
                .setBillingAddress(address)
                .setCourier(new CustomCourier())
                .createOrder();

        context.checking(new Expectations() {{
            oneOf(paymentMethod).charge(new BigDecimal("22.00"), creditCardDetails, address);
        }});

        small1.process();

        // Small order + giftwrap
        Order small2 = new OrderBuilder()
                .addItem(items.get(0))
                .addItem(items.get(1))
                .setCreditCardDetails(creditCardDetails)
                .setPaymentMethod(paymentMethod)
                .setBillingAddress(address)
                .setCourier(new CustomCourier())
                .withGiftWrapping()
                .createOrder();

        context.checking(new Expectations() {{
            oneOf(paymentMethod).charge(new BigDecimal("25.00"), creditCardDetails, address);
        }});

        small2.process();

        // Bulk order
        Order big1 = new OrderBuilder()
                .addItems(items)
                .setCreditCardDetails(creditCardDetails)
                .setPaymentMethod(paymentMethod)
                .setBillingAddress(address)
                .setCourier(new CustomCourier())
                .createOrder();

        context.checking(new Expectations() {{
            oneOf(paymentMethod).charge(new BigDecimal("71.55"), creditCardDetails, address);
        }});

        big1.process();

        // Bulk order + discount
        Order big2 = new OrderBuilder()
                .addItems(items)
                .setCreditCardDetails(creditCardDetails)
                .setPaymentMethod(paymentMethod)
                .setBillingAddress(address)
                .setCourier(new CustomCourier())
                .setBulkDiscount(new BigDecimal("1.55"))
                .createOrder();

        context.checking(new Expectations() {{
            oneOf(paymentMethod).charge(new BigDecimal("70.00"), creditCardDetails, address);
        }});

        big2.process();
    }
}

class CustomCourier implements Courier {

    @Override
    public void send(Parcel shipment, Address shippingAddress) {

    }

    @Override
    public BigDecimal deliveryCharge() {
        return BigDecimal.ZERO;
    }
}
