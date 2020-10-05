package seedu.momentum.model.timer;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTime;

/**
 * Represents a timer in momentum.
 */
public class Timer {
    private DateTime startDateTime;
    private DateTime stopDateTime;
    private boolean isRunning;

    /**
     * Constructs a timer with default settings.
     */
    public Timer() {
        this.startDateTime = Clock.now();
        this.stopDateTime = Clock.now();
        this.isRunning = false;
    }

    /**
     * Constructs a timer with the provided data.
     *
     * @param startDateTime The dateTime when the timer was started.
     * @param stopDateTime The dateTime when the timer was stopped.
     * @param isRunning Whether the timer is running.
     */
    public Timer(DateTime startDateTime, DateTime stopDateTime, boolean isRunning) {
        this.startDateTime = startDateTime;
        this.stopDateTime = stopDateTime;
        this.isRunning = isRunning;
    }

    /**
     * Start the timer.
     */
    public Timer start() {
        assert (!isRunning);
        return new Timer(Clock.now(), Clock.now(), true);
    }

    /**
     * Stop the timer.
     */
    public Timer stop() {
        assert (isRunning);
        return new Timer(startDateTime, Clock.now(), false);
    }

    public DateTime getStartTime() {
        assert (startDateTime != null);
        return startDateTime;
    }

    public DateTime getStopTime() {
        assert (stopDateTime != null);
        return stopDateTime;
    }

    /**
     * Returns the length of dateTime tracked in this timer, in (@code unit) units.
     *
     * @param unit The units for the length of dateTime.
     * @return The length of dateTime in the provided units.
     */
    public long getTimeBetween(ChronoUnit unit) {
        assert (!isRunning);
        return unit.between(startDateTime.getDateTime(), stopDateTime.getDateTime());
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timer other = (Timer) o;

        return startDateTime.equals(other.startDateTime)
                && Objects.equals(startDateTime, other.startDateTime)
                && Objects.equals(stopDateTime, other.stopDateTime)
                && Objects.equals(isRunning, other.isRunning);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, stopDateTime, isRunning);
    }
}
