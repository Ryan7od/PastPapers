package ic.doc.streaming;

import ic.doc.User;
import ic.doc.movies.Movie;

public interface Log {
    public void logWatched(User user, Movie movie);
}
