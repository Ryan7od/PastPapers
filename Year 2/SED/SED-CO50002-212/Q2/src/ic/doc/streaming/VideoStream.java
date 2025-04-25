package ic.doc.streaming;

import ic.doc.movies.Movie;

public class VideoStream{
  private final Movie movie;

  public VideoStream(Movie movie) {
    this.movie = movie;
  }

  public Movie movie() {
    return movie;
  }
}
