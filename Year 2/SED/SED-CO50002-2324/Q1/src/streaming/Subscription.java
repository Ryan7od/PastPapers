package streaming;

public class Subscription {

  private final int devices;
  private final Quality quality;
  private final boolean hasNews;
  private final boolean hasComedy;
  private final boolean hasMovies;
  private final boolean hasFormulaOne;
  private final boolean hasFootball;
  private final PaymentMethod paymentMethod;

  private Subscription(
          int devices,
          Quality quality,
          boolean hasNews,
          boolean hasComedy,
          boolean hasMovies,
          boolean hasFormulaOne,
          boolean hasFootball,
          PaymentMethod paymentMethod) {
    this.devices = devices;
    this.quality = quality;
    this.hasNews = hasNews;
    this.hasComedy = hasComedy;
    this.hasMovies = hasMovies;
    this.hasFormulaOne = hasFormulaOne;
    this.hasFootball = hasFootball;
    this.paymentMethod = paymentMethod;
  }

  public static class Builder {
    private int devices = 1;
    private Quality quality = Quality.STANDARD_DEFINITION;
    private boolean hasNews = false;
    private boolean hasComedy = false;
    private boolean hasMovies = false;
    private boolean hasFormulaOne = false;
    private boolean hasFootball = false;
    private PaymentMethod paymentMethod;

    public Builder setDevices(int devices) {
      this.devices = devices;
      return this;
    }

    public Builder setQuality(Quality quality) {
      this.quality = quality;
      return this;
    }

    public Builder setHasNews(boolean hasNews) {
      this.hasNews = hasNews;
      return this;
    }

    public Builder setHasComedy(boolean hasComedy) {
      this.hasComedy = hasComedy;
      return this;
    }

    public Builder setHasMovies(boolean hasMovies) {
      this.hasMovies = hasMovies;
      return this;
    }

    public Builder setHasFormulaOne(boolean hasFormulaOne) {
      this.hasFormulaOne = hasFormulaOne;
      return this;
    }

    public Builder setHasFootball(boolean hasFootball) {
      this.hasFootball = hasFootball;
      return this;
    }

    public Builder setPaymentMethod(PaymentMethod paymentMethod) {
      this.paymentMethod = paymentMethod;
      return this;
    }

    public Subscription createSubscription() {
      return new Subscription(devices, quality, hasNews, hasComedy, hasMovies, hasFormulaOne, hasFootball, paymentMethod);
    }
  }

  public boolean includes(Content content) {
    // In a full implementation, this method would check
    // if the content is included in the subscription.
    return true;
  }

  public void renew() {
    int amount = hasFootball ? 4 + devices : 2 + devices;
    paymentMethod.charge(amount);
  }
}

