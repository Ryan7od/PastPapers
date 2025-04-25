package ic.doc;

import ic.doc.movies.Movie;
import ic.doc.streaming.Log;
import ic.doc.streaming.PlaybackEventLog;
import ic.doc.streaming.StreamTracker;
import ic.doc.streaming.VideoStream;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class VideoStreamer {
  private final MovieProvider movieProvider;
  private final Map<VideoStream, StreamTracker> currentStreams = new HashMap<>();
  private final Log playbackEvents;

  public List<Movie> getSuggestedMovies(User user) {
    List<Movie> recommendations = movieProvider.recommendedMoviesFor(user);

    // sort the list of suggestions in descending order of number of views
    List<Movie> suggestions =  new ArrayList<>(recommendations);
    suggestions.sort(Comparator.comparing(Movie::numberOfViews).reversed());
    return suggestions;
  }

  public VideoStream startStreaming(Movie movie, User user, Clock clock) {
    VideoStream stream = new VideoStream(movie);
    currentStreams.put(stream, new StreamTracker(user, clock));
    return stream;
  }

  public void stopStreaming(VideoStream stream, Clock clock) {
    StreamTracker streamTracker = currentStreams.remove(stream);
    LocalTime endTime = clock.now();
    long minutesWatched = ChronoUnit.MINUTES.between(streamTracker.startTime(), endTime);
    if (minutesWatched > 15) {
      playbackEvents.logWatched(streamTracker.user(), stream.movie());
    }
  }

  public VideoStreamer(MovieProvider movieProvider, Log log) {
    this.movieProvider = movieProvider;
    this.playbackEvents = log;
  }
}
