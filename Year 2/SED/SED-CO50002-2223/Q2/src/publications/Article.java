package publications;

import java.util.Objects;

public class Article {

  private final String title;

  public Article(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "Article: " + title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Article article = (Article) o;
    return Objects.equals(title, article.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title);
  }
}
