package ic.doc;

public record ClientBookPair(Client client, Book book) {
    public Client getClient() {
        return client;
    }

    public Book getBook() {
        return book;
    }
}
