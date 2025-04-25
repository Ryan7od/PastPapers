package streaming;

public class SubscriptionFactory {
    public static Subscription createSubscription(SubscriptionType type, PaymentMethod payment) {
        return switch (type) {
            case BASIC ->
                    new Subscription.Builder().setDevices(1).setQuality(Quality.STANDARD_DEFINITION).setHasNews(true).setHasComedy(true).setPaymentMethod(payment).createSubscription();
            case SPORTS ->
                    new Subscription.Builder().setDevices(2).setQuality(Quality.HIGH_DEFINITION).setHasFormulaOne(true).setHasFootball(true).setPaymentMethod(payment).createSubscription();
            case PREMIUM ->
                    new Subscription.Builder().setDevices(5).setQuality(Quality.ULTRA_HD).setHasNews(true).setHasComedy(true).setHasMovies(true).setHasFormulaOne(true).setHasFootball(true).setPaymentMethod(payment).createSubscription();
        };
    }
}

