package ic.doc;

public interface Warehouse {
    void dispatch(Book book, int num, Client client);
    int checkStockLevel(Book book);
}
