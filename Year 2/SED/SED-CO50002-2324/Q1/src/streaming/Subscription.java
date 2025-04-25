package streaming;

public class Subscription {

  private final int devices;
  private final Quality quality;
  private final boolean hasNews;
  private final boolean hasComedy;
  private final boolean hasMovies;
  private final boolean hasFormulaOne;
  private final boolean hasFootball;

  public Subscription(
      int devices,
      Quality quality,
      boolean hasNews,
      boolean hasComedy,
      boolean hasMovies,
      boolean hasFormulaOne,
      boolean hasFootball) {
    this.devices = devices;
    this.quality = quality;
    this.hasNews = hasNews;
    this.hasComedy = hasComedy;
    this.hasMovies = hasMovies;
    this.hasFormulaOne = hasFormulaOne;
    this.hasFootball = hasFootball;
  }

  public boolean includes(Content content) {
    // In a full implementation, this method would check
    // if the content is included in the subscription.
    return true;
  }

  public void renew() {
    System.out.println("Renewing your subscription...");
  }
}

