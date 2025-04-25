package ic.doc;

import ic.doc.movies.Certification;
import ic.doc.movies.Movie;
import ic.doc.streaming.Log;
import ic.doc.streaming.VideoStream;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class VideoStreamerTest {
    JUnitRuleMockery context = new JUnitRuleMockery();
    Log log = context.mock(Log.class);

    @Test
    public void allowsUserToStreamSuggestedMovies() {

        VideoStreamer streamer = new VideoStreamer(new DefaultMovieProvider(), log);
        User user = new User("Adam", 9);

        List<Movie> movies = streamer.getSuggestedMovies(user);
        VideoStream stream = streamer.startStreaming(movies.getFirst(), user, new DefaultClock());

        // adam watches the movie

        streamer.stopStreaming(stream, new DefaultClock());
    }

    @Test
    public void recommendedMoviesSortedOnViewsDescending() {
        VideoStreamer streamer = new VideoStreamer(new CustomMovieProvider(), log);
        User user = new User("Adam", 9);

        List<Movie> movies = streamer.getSuggestedMovies(user);

        Assert.assertEquals("Movie B", movies.get(0).title());
        Assert.assertEquals("Movie A", movies.get(1).title());
        Assert.assertEquals("Movie C", movies.get(2).title());
    }

    @Test
    public void addedToPlaybackOnOverFifteenMinuteWatch() {
        VideoStreamer streamer = new VideoStreamer(new CustomMovieProvider(), log);
        User user = new User("Adam", 9);

        CustomClock clock = new CustomClock(LocalTime.of(10, 15));
        Movie movie = new Movie("Movie A", "Description A", 100, List.of(), Set.of(), List.of(), Certification.TWELVE);

        context.checking(new Expectations() {{
            exactly(0).of(log).logWatched(with(any(User.class)), with(any(Movie.class)));
        }});

        VideoStream stream1 = streamer.startStreaming(movie, user, clock);
        clock.increment(ChronoUnit.MINUTES, 5);
        streamer.stopStreaming(stream1, clock);

        context.checking(new Expectations() {{
            exactly(1).of(log).logWatched(with(any(User.class)), with(any(Movie.class)));
        }});

        VideoStream stream2 = streamer.startStreaming(movie, user, clock);
        clock.increment(ChronoUnit.MINUTES, 17);
        streamer.stopStreaming(stream2, clock);
    }

    private class CustomClock implements Clock {
        private LocalTime now;

        public CustomClock (LocalTime now) {
            this.now = now;
        }

        public void increment(ChronoUnit unit, int time) {
            now = now.plus(time, unit);
        }

        public LocalTime now() {
            return now;
        }
    }

    private class CustomMovieProvider implements MovieProvider {

        @Override
        public List<Movie> recommendedMoviesFor(User user) {
            return List.of(
                    new Movie("Movie A", "Description A", 100, List.of(), Set.of(), List.of(), Certification.TWELVE),
                    new Movie("Movie B", "Description B", 7029, List.of(), Set.of(), List.of(), Certification.TWELVE),
                    new Movie("Movie C", "Description C", 3, List.of(), Set.of(), List.of(), Certification.TWELVE)
            );
        }
    }
}
