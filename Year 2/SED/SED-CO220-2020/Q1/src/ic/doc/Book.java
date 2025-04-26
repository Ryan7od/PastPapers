package ic.doc;

public class Book {

  private final String title;
  private final String authors;
  private final double unitPrice;

  public Book(String title, String authors, double unitPrice) {
    this.title = title;
    this.authors = authors;
    this.unitPrice = unitPrice;
  }

  public String title() {
    return title;
  }

  public String authors() {
    return authors;
  }

  public double price() {
    return unitPrice;
  }
}
