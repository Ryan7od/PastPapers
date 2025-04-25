package ic.doc;

public record Bid(int amount, Bidder bidder) {
    public int getAmount() {
        return amount;
    }

    public Bidder getBidder() {
        return bidder;
    }
}
