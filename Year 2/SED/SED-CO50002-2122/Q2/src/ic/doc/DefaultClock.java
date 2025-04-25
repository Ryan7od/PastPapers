package ic.doc;

import java.time.LocalTime;

public class DefaultClock implements Clock {
    public LocalTime now() {
        return LocalTime.now();
    }
}
