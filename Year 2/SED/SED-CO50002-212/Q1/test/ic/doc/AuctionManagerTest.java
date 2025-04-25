package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AuctionManagerTest {
  JUnitRuleMockery context = new JUnitRuleMockery();
  PaymentSystem paymentSystem = context.mock(PaymentSystem.class);
  Item item = context.mock(Item.class);
  Seller seller = context.mock(Seller.class);
  Dispatcher dispatcher = context.mock(Dispatcher.class);
  Bidder alice = context.mock(Bidder.class, "alice");
  Bidder bob = context.mock(Bidder.class, "bob");
  Bidder david = context.mock(Bidder.class, "david");

  @Test
  public void biddingChargesAndAccepts() {
    AuctionManager am = new AuctionManager(paymentSystem, dispatcher);

    context.checking(new Expectations() {{
      exactly(1).of(paymentSystem).charge(10, alice);
      exactly(1).of(alice).BID_ACCEPTED();
    }});

    am.bid(10, alice);
  }

  @Test
  public void biddigRejectsIfTooLow() {
    AuctionManager am = new AuctionManager(paymentSystem, dispatcher);

    context.checking(new Expectations() {{
      ignoring(paymentSystem).charge(10, alice);
      ignoring(alice).BID_ACCEPTED();
    }});

    am.bid(10, alice);

    context.checking(new Expectations() {{
      exactly(0).of(paymentSystem).charge(10, alice);
      exactly(1).of(bob).BID_TOO_LOW();
    }});

    am.bid(5, bob);
  }

  @Test
  public void AuctionManagerHoldsSellerAndBidderInfo() {
    AuctionManager am = new AuctionManager(paymentSystem, dispatcher);

    context.checking(new Expectations() {{
      ignoring(paymentSystem).charge(10, alice);
      ignoring(alice).BID_ACCEPTED();
      ignoring(paymentSystem).charge(5, bob);
      ignoring(bob).BID_TOO_LOW();
      ignoring(paymentSystem).charge(15, david);
      ignoring(david).BID_ACCEPTED();
    }});

    am.startAuction(item, seller);
    am.bid(10, alice);
    am.bid(5, bob);
    am.bid(15, david);

    List<Bid> expectedList = new ArrayList<>();

    expectedList.add(new Bid(10, alice));
    expectedList.add(new Bid(15, david));

    Assert.assertEquals(expectedList, am.getBids());
    Assert.assertEquals(item, am.getItem());
    Assert.assertEquals(seller, am.getSeller());
  }

  @Test
  public void endAuctionBehavesCorrectly() {
    AuctionManager am = new AuctionManager(paymentSystem, dispatcher);

    context.checking(new Expectations() {{
      ignoring(paymentSystem).charge(10, alice);
      ignoring(alice).BID_ACCEPTED();
      ignoring(paymentSystem).charge(5, bob);
      ignoring(bob).BID_TOO_LOW();
      ignoring(paymentSystem).charge(15, david);
      ignoring(david).BID_ACCEPTED();
    }});

    am.startAuction(item, seller);
    am.bid(10, alice);
    am.bid(5, bob);
    am.bid(15, david);

    context.checking(new Expectations() {{
      exactly(1).of(paymentSystem).pay(15, seller);
      exactly(1).of(paymentSystem).pay(10, alice);
      exactly(1).of(dispatcher).dispatch(item, david);
    }});

    am.endAuction();
  }

}

