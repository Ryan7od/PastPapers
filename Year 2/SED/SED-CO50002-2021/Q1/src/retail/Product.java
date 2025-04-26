package retail;

import java.math.BigDecimal;

public class Product {

  private final String description;
  private final BigDecimal unitPrice;

  public Product(String description, BigDecimal unitPrice) {
    this.description = description;
    this.unitPrice = unitPrice;
  }

  public BigDecimal unitPrice() {
    return unitPrice;
  }
}
