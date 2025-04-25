package flights;

public enum Airport {
  LHR,
  JFK,
  SFO,
  CDG;

  public int distanceTo(Airport destination) {
    return switch (this) {
      case LHR -> switch (destination) {
        case JFK -> 5550;
        case CDG -> 385;
        case SFO -> 8640;
        case LHR -> 0;
      };
      case JFK -> switch (destination) {
        case JFK -> 0;
        case CDG -> 5850;
        case SFO -> 4150;
        case LHR -> 5550;
      };
      case SFO -> switch (destination) {
        case JFK -> 4150;
        case CDG -> 8985;
        case SFO -> 0;
        case LHR -> 8640;
      };
      case CDG -> switch (destination) {
        case JFK -> 5850;
        case CDG -> 0;
        case SFO -> 8985;
        case LHR -> 385;
      };
    };
  }
}
