package retail;

import java.math.BigDecimal;

public interface PaymentMethod {
    void charge(BigDecimal amount, CreditCardDetails account, Address billingAddress);
}
