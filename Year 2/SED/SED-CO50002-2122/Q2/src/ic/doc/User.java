package ic.doc;

public class User {
  private final String username;
  private final int age;

  public User(String username, int age) {
    this.username = username;
    this.age = age;
  }

  @Override
  public String toString() {
    return username;
  }

  public boolean isChild() {
    return age < 15;
  }
}
