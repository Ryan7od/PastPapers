package streaming;

public class CreditCard implements PaymentMethod {
    public CreditCard(String name) {
        this.name = name;
    }

    private final String name;

    @Override
    public void charge(double amount) {
        System.out.println("Charged " + amount + " to credit card " + name);
    }
}
