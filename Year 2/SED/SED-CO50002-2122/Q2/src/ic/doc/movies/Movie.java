package ic.doc.movies;

import ic.doc.awards.Award;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ic.doc.movies.Certification.*;

public class Movie {
    private final String title;
    private final String description;
    private final Integer views;
    private final List<Actor> cast;
    private final Set<Genre> genres;
    private final List<Award> awards;
    private final Certification certificate;

    public Movie(String title,
                 String description,
                 Integer views,
                 List<Actor> cast,
                 Set<Genre> genres,
                 List<Award> awards,
                 Certification certificate) {
        this.title = title;
        this.description = description;
        this.views = views;
        this.cast = cast;
        this.genres = genres;
        this.awards = awards;
        this.certificate = certificate;
    }

    public boolean stars(Actor actor) {
        // We count an actor as 'starring' in a movie if they are
        // one of the first two people in the cast list.
        return cast.subList(0,2).contains(actor);
    }

    public boolean matchesGenre(Genre genre) {
        return genres.contains(genre);
    }

    public boolean isSuitableForChildren() {
        return Set.of(UNIVERSAL, PARENTAL_GUIDANCE, TWELVE).contains(certificate);
    }

    public Integer numberOfViews() {
        return views;
    }

    public String title() {
        return title;
    }

    @Override
    public String toString() {
        return title.toUpperCase() + "\n - " + description;
    }
}

