package com.londonstockexchange;

public class StockPrice {

  private final TickerSymbol stock;
  private final int price;

  public StockPrice(TickerSymbol stock, int price) {
    this.stock = stock;
    this.price = price;
  }

  public int inPennies() {
    return price * 100;
  }
}
