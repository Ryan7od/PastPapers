package com.londonstockexchange;

public class StockMarketDataFeed {

  private static final StockMarketDataFeed INSTANCE = new StockMarketDataFeed();

  private StockMarketDataFeed() {}

  public static StockMarketDataFeed getInstance() {
    return INSTANCE;
  }

  public StockPrice currentPriceFor(TickerSymbol stock) {
    return new StockPrice(stock, (int) (Math.random() * 1000));
  }
}
