package publications;

import org.jmock.Expectations;
import org.jmock.api.Expectation;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class ArticleRepositoryTest {

  @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

  Summariser summariser = context.mock(Summariser.class);
  Subscriber bob = context.mock(Subscriber.class, "bob");
  Subscriber alice = context.mock(Subscriber.class, "alice");

  Article article = new Article("ARTICLE");

  @Test
  public void ArticlesSummarisedOnPublication() {
    ArticleRepository repo = new ArticleRepository(summariser);

    context.checking(new Expectations() {{
      exactly(1).of(summariser).summarise(article);
    }});
    repo.publish(article);
  }

  @Test
  public void SubscribersNotifiedOnMatchingArticles() {
    ArticleRepository repo = new ArticleRepository(summariser);

    repo.subscribeTo(alice, "testing");
    repo.subscribeTo(bob, "testing");

    context.checking(new Expectations() {{
      exactly(1).of(summariser).summarise(article);
      will(returnValue(new String[]{"testing"}));
      exactly(1).of(alice).notify(article);
      exactly(1).of(bob).notify(article);
    }});
    repo.publish(article);
  }

  @Test
  public void SubscribersNotNotifiedOnNonMatchingArticles() {
    ArticleRepository repo = new ArticleRepository(summariser);

    repo.subscribeTo(alice, "not testing");
    repo.subscribeTo(bob, "testing");

    context.checking(new Expectations() {{
      exactly(1).of(summariser).summarise(article);
      will(returnValue(new String[]{"testing"}));
      exactly(0).of(alice).notify(article);
      exactly(1).of(bob).notify(article);
    }});
    repo.publish(article);
  }

  @Test
  public void SubscribersNotifiedOnceOnMultipleMatches() {
    ArticleRepository repo = new ArticleRepository(summariser);

    repo.subscribeTo(alice, "Java");
    repo.subscribeTo(alice, "testing");
    repo.subscribeTo(bob, "testing");

    context.checking(new Expectations() {{
      exactly(1).of(summariser).summarise(article);
      will(returnValue(new String[]{"testing", "Java"}));
      exactly(1).of(alice).notify(article);
      exactly(1).of(bob).notify(article);
    }});
    repo.publish(article);
  }

  @Test
  public void UnsubscribeStopsNotifications() {
    ArticleRepository repo = new ArticleRepository(summariser);

    repo.subscribeTo(alice, "Java");
    repo.subscribeTo(alice, "testing");
    repo.subscribeTo(bob, "testing");

    context.checking(new Expectations() {{
      exactly(1).of(summariser).summarise(article);
      will(returnValue(new String[]{"testing", "Java"}));
      exactly(1).of(alice).notify(article);
      exactly(1).of(bob).notify(article);
      exactly(1).of(summariser).summarise(article);
      will(returnValue(new String[]{"testing", "Python"}));
      exactly(1).of(bob).notify(article);
    }});

    repo.publish(article);
    repo.unsubscribe(alice, "testing");
    repo.publish(article);
  }
}
