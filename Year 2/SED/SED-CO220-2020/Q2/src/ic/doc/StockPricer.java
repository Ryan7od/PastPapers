package ic.doc;

import com.londonstockexchange.TickerSymbol;

public interface StockPricer {
    int currentPriceFor(String ticker);
}
