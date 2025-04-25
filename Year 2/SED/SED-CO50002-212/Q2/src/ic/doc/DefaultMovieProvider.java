package ic.doc;

import ic.doc.movies.Movie;

import java.util.List;

public class DefaultMovieProvider implements MovieProvider {
    @Override
    public List<Movie> recommendedMoviesFor(User user) {
        return MediaLibrary.getInstance().recommendedMoviesFor(user);
    }
}
