package ic.doc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class AuctionManager {
    private final PaymentSystem paymentSystem;
    private int highestBid = -1;
    private List<Bid> chargedBids = new ArrayList<>();

    private Item item = null;
    private Seller seller = null;
    private Dispatcher dispatcher;

    public AuctionManager(PaymentSystem paymentSystem, Dispatcher dispatcher) {
        this.paymentSystem = paymentSystem;
        this.dispatcher = dispatcher;
    }

    public void bid(int amount, Bidder bidder) {
        if (amount > highestBid) {
            paymentSystem.charge(amount, bidder);
            bidder.BID_ACCEPTED();
            chargedBids.add(new Bid(amount, bidder));
            highestBid = amount;
        } else {
            bidder.BID_TOO_LOW();
        }
    }

    public void startAuction(Item item, Seller seller) {
        this.item = item;
        this.seller = seller;
    }

    public void endAuction() {
        if (chargedBids.isEmpty()) return;

        for (int i = 0; i < chargedBids.size() - 1; i++) {
            paymentSystem.pay(chargedBids.get(i).getAmount(), chargedBids.get(i).getBidder());
        }

        Bid topBid = chargedBids.get(chargedBids.size()-1);
        paymentSystem.pay(topBid.getAmount(), seller);
        dispatcher.dispatch(item, topBid.getBidder());
    }

    public List<Bid> getBids() {
        return Collections.unmodifiableList(chargedBids);
    }

    public Item getItem() {
        return item;
    }

    public Seller getSeller() {
        return seller;
    }
}
