package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

public class OrderProcessorTest {

  static final Book DESIGN_PATTERNS_BOOK =
      new Book("Design Patterns", "Gamma, Helm, Johnson and Vlissides", 25.99);
  static final Book LEGACY_CODE_BOOK =
      new Book("Working Effectively with Legacy Code", "Feathers", 29.99);

  static final Customer ALICE = new Customer("Alice Jones");
  static final Customer BOB = new Customer("Bob Smith");

  JUnitRuleMockery context = new JUnitRuleMockery();
  Client alice = context.mock(Client.class, "Alice");
  Client bob = context.mock(Client.class, "Bob");
  Warehouse warehouse = context.mock(Warehouse.class);
  PaymentSystem paymentSystem = context.mock(PaymentSystem.class);
  Buyer buyer = context.mock(Buyer.class);

  // write your tests here
  @Test
  public void willFillOrderIfInWarehouse() {
    OrderProcessor op = new OrderProcessor(warehouse, paymentSystem, buyer);

    context.checking(new Expectations() {{
      oneOf(warehouse).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(0));
      never(warehouse).dispatch(LEGACY_CODE_BOOK, 1, bob);
    }});

    op.order(LEGACY_CODE_BOOK, 1, bob);

    context.checking(new Expectations() {{
      oneOf(warehouse).checkStockLevel(DESIGN_PATTERNS_BOOK);
      will(returnValue(3));
      oneOf(warehouse).dispatch(DESIGN_PATTERNS_BOOK, 2, alice);
    }});

    op.order(DESIGN_PATTERNS_BOOK, 2, alice);
  }

  @Test
  public void willChargeIfDispatched() {
    OrderProcessor op = new OrderProcessor(warehouse, paymentSystem, buyer);

    context.checking(new Expectations() {{
      oneOf(warehouse).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(0));
      never(warehouse).dispatch(LEGACY_CODE_BOOK, 1, bob);
    }});

    op.order(LEGACY_CODE_BOOK, 1, bob);

    context.checking(new Expectations() {{
      oneOf(warehouse).checkStockLevel(DESIGN_PATTERNS_BOOK);
      will(returnValue(3));
      oneOf(paymentSystem).charge(DESIGN_PATTERNS_BOOK.price()*2, alice);
      oneOf(warehouse).dispatch(DESIGN_PATTERNS_BOOK, 2, alice);
    }});

    op.order(DESIGN_PATTERNS_BOOK, 2, alice);
  }

  @Test
  public void ordersAreKeptAndFilledAsAvailable() {
    OrderProcessor op = new OrderProcessor(warehouse, paymentSystem, buyer);

    context.checking(new Expectations() {{
      oneOf(warehouse).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(0));
      oneOf(buyer).buyMoreOf(LEGACY_CODE_BOOK);
      never(warehouse).dispatch(LEGACY_CODE_BOOK, 1, bob);
    }});

    op.order(LEGACY_CODE_BOOK, 1, bob);

    context.checking(new Expectations() {{
      oneOf(warehouse).checkStockLevel(DESIGN_PATTERNS_BOOK);
      will(returnValue(3));
      oneOf(paymentSystem).charge(DESIGN_PATTERNS_BOOK.price()*2, alice);
      oneOf(warehouse).dispatch(DESIGN_PATTERNS_BOOK, 2, alice);
    }});

    op.order(DESIGN_PATTERNS_BOOK, 2, alice);

    context.checking(new Expectations() {{
      oneOf(warehouse).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(0));
    }});

    op.newBooksArrived();

    context.checking(new Expectations() {{
      oneOf(warehouse).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(5));
      oneOf(paymentSystem).charge(LEGACY_CODE_BOOK.price()*1, bob);
      oneOf(warehouse).dispatch(LEGACY_CODE_BOOK, 1, bob);
    }});

    op.newBooksArrived();
  }

}
