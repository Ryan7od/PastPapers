package retail;

import java.math.BigDecimal;

public class DefaultPaymentMethod implements PaymentMethod {
    @Override
    public void charge(BigDecimal amount, CreditCardDetails account, Address billingAddress) {
        CreditCardProcessor.getInstance().charge(amount, account, billingAddress);
    }
}
