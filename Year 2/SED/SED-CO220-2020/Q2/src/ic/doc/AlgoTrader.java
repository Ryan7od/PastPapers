package ic.doc;

import com.londonstockexchange.StockMarketDataFeed;
import com.londonstockexchange.StockPrice;
import com.londonstockexchange.TickerSymbol;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AlgoTrader {
  private final StockPricer stockPricer = new DefualtStockPricer();
  private final List<String> stocksToWatch =
      List.of("GOOG", "MSFT", "APPL");

  private final Map<String, Integer> lastPrices = new HashMap<>();
  private final SimpleBroker broker = new SimpleBroker();

  public void trade() {

    for (String stock : stocksToWatch) {

      int price = stockPricer.currentPriceFor(stock);

      if (isRising(stock, price)) {
        broker.buy(String.valueOf(stock));
      }

      if (isFalling(stock, price)) {
        broker.sell(String.valueOf(stock));
      }

      lastPrices.put(stock, price);
    }
  }

  private boolean isFalling(String stock, int price) {
    int lastPrice = lastPrices.getOrDefault(stock, 0);
    return price < lastPrice;
  }

  private boolean isRising(String stock, int price) {
    int lastPrice = lastPrices.getOrDefault(stock, Integer.MAX_VALUE);
    return price > lastPrice;
  }

  public static void main(String[] args) {
    new AlgoTrader().start();
  }

  // code below here is not important for the exam

  private void logPrices(TickerSymbol stock, StockPrice price, int lastPrice) {
    System.out.println(
        String.format("%s used to be %s, now %s ", stock, lastPrice, price.inPennies()));
  }

  private void start() {

    // run trade() every minute

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    executorService.scheduleAtFixedRate(this::trade, 0, 60, TimeUnit.SECONDS);
  }
}
