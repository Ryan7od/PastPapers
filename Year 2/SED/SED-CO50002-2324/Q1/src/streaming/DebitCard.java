package streaming;

public class DebitCard implements PaymentMethod {
    public DebitCard(String name) {
        this.name = name;
    }

    private static String name;

    @Override
    public void charge(double amount) {
        System.out.println("Charged " + amount + " to debit card " + name);
    }
}
