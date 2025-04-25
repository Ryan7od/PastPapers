package streaming;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

public class SubscriptionTest {
    JUnitRuleMockery context = new JUnitRuleMockery();
    PaymentMethod card = context.mock(PaymentMethod.class);

    @Test
    public void BasicSubscriptionCosts3() {
        Subscription basic = SubscriptionFactory.createSubscription(SubscriptionType.BASIC, card);

        context.checking(new Expectations() {{
            exactly(1).of(card).charge(3.0);
        }});

        basic.renew();
    }

    @Test
    public void SportsSubscriptionCosts6() {
        Subscription sports = SubscriptionFactory.createSubscription(SubscriptionType.SPORTS, card);

        context.checking(new Expectations() {{
            exactly(1).of(card).charge(6.0);
        }});

        sports.renew();
    }

    @Test
    public void PremiumSubscriptionCosts9() {
        Subscription premium = SubscriptionFactory.createSubscription(SubscriptionType.PREMIUM, card);

        context.checking(new Expectations() {{
            exactly(1).of(card).charge(9.0);
        }});

        premium.renew();
    }

    @Test
    public void LotsOfScreensAddToPrice() {
        Subscription sub = new Subscription.Builder().setDevices(35).setQuality(Quality.STANDARD_DEFINITION).setPaymentMethod(card).createSubscription();

        context.checking(new Expectations() {{
            exactly(1).of(card).charge(37.0);
        }});

        sub.renew();
    }

    @Test
    public void FootballAdds2ToPrice() {
        Subscription sub = new Subscription.Builder().setDevices(35).setQuality(Quality.STANDARD_DEFINITION).setHasFootball(true).setPaymentMethod(card).createSubscription();

        context.checking(new Expectations() {{
            exactly(1).of(card).charge(39.0);
        }});

        sub.renew();
    }
}
