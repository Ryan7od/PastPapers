package ic.doc.streaming;

import ic.doc.User;
import ic.doc.movies.Movie;

public class PlaybackEventLog implements Log {
    public void logWatched(User user, Movie movie) {
        System.out.printf("%s enjoyed %s %n", user, movie.title());
    }
    public void logRejection(User user, Movie movie) {
        System.out.printf("%s did not like %s %n", user, movie.title());
    }
}



