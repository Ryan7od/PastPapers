package ic.doc;

import java.util.HashMap;
import java.util.List;

public class OrderProcessor {
    private final Warehouse warehouse;
    private final PaymentSystem paymentSystem;
    private final Buyer buyer;

    private final HashMap<ClientBookPair, Integer> orders = new HashMap<>();

    public OrderProcessor(Warehouse warehouse, PaymentSystem paymentSystem, Buyer buyer) {
        this.warehouse = warehouse;
        this.paymentSystem = paymentSystem;
        this.buyer = buyer;
    }

    public void order(Book book, int num, Client client) {
        int numAvailable = warehouse.checkStockLevel(book);
        if (numAvailable >= num) {
            paymentSystem.charge(book.price()*num, client);
            warehouse.dispatch(book, num, client);
        } else if (numAvailable > 0) {
            paymentSystem.charge(book.price()*numAvailable, client);
            warehouse.dispatch(book, num, client);
            addOrder(client, book, num-numAvailable);
        } else {
            addOrder(client, book, num);
        }
    }

    public void newBooksArrived() {
        for (ClientBookPair pair : orders.keySet()) {
            int num = orders.getOrDefault(pair, 0);
            orders.remove(pair);
            order(pair.getBook(), num, pair.getClient());
        }
    }

    private void addOrder(Client client, Book book, int num) {
        ClientBookPair pair = new ClientBookPair(client, book);
        orders.put(pair, orders.getOrDefault(pair, 0) + num);
    }
}
