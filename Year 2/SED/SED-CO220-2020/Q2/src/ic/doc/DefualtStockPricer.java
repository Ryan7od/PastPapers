package ic.doc;

import com.londonstockexchange.StockMarketDataFeed;
import com.londonstockexchange.TickerSymbol;

public class DefualtStockPricer implements StockPricer {
    @Override
    public int currentPriceFor(String ticker) {
        return StockMarketDataFeed.getInstance().currentPriceFor(convertStringToTicker(ticker)).inPennies();
    }

    private TickerSymbol convertStringToTicker(String string) {
        return switch(string) {
            case "AMZN":
                yield TickerSymbol.AMZN;
            case "APPL":
                yield TickerSymbol.APPL;
            case "FB":
                yield TickerSymbol.FB;
            case "GOOG":
                yield TickerSymbol.GOOG;
            case "MSFT":
                yield TickerSymbol.MSFT;
            case "NFLX":
                yield TickerSymbol.NFLX;
            default:
                throw new IllegalStateException("Unexpected ticker: " + string);
        };
    }
}
