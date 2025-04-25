package ic.doc;

public interface PaymentSystem {
    public void charge(int amount, Bidder bidder);
    public void pay(int amount, Bidder bidder);
    public void pay(int amount, Seller seller);
}
