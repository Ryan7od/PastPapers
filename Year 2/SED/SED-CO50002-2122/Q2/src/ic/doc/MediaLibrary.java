package ic.doc;

import ic.doc.awards.Oscar;
import ic.doc.movies.Actor;
import ic.doc.movies.Genre;
import ic.doc.movies.Movie;
import ic.doc.streaming.VideoStream;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ic.doc.movies.Certification.PARENTAL_GUIDANCE;
import static ic.doc.movies.Certification.TWELVE_A;
import static java.util.Collections.EMPTY_LIST;

public class MediaLibrary {

    // This code is simplified for the purposes of the exam - you can imagine in real life
    // this class would be much bigger and more sophisticated, with much more data.

    private static final MediaLibrary INSTANCE = new MediaLibrary();

    public static MediaLibrary getInstance() {
        return INSTANCE;
    }

    private final List<Movie> topMovies;

    public MediaLibrary() {
        topMovies = List.of(
                new Movie("Jurassic Park",
                        "A pragmatic paleontologist touring an almost complete theme park on an " +
                                "island in Central America is tasked with protecting a couple of kids after " +
                                "a power failure causes the park's cloned dinosaurs to run loose.",
                        2342384,
                        List.of(new Actor("Richard Attenborough"),
                                new Actor("Laura Dern"),
                                new Actor("Sam Neill"),
                                new Actor("Jeff Goldblum")),
                        Set.of(Genre.ADVENTURE),
                        List.of(Oscar.forBest("Visual Effects")),
                        PARENTAL_GUIDANCE
                ),
                new Movie("No Time To Die",
                        "Another installment of the James Bond franchise",
                        1342365,
                        List.of(new Actor("Daniel Craig")),
                        Set.of(Genre.ACTION, Genre.ADVENTURE),
                        EMPTY_LIST,
                        TWELVE_A
                ));
    }

    public List<Movie> recommendedMoviesFor(User user) {

        // A sophisticated ML algorithm runs and then recommends...

        if (user.isChild()) {
            return topMovies
                    .stream()
                    .filter(Movie::isSuitableForChildren)
                    .collect(Collectors.toList());
        } else {
            return topMovies;
        }
    }
}
