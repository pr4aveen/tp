package seedu.momentum.model.timer;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.momentum.commons.core.Clock;

/**
 * Represents a timer in momentum.
 */
public class Timer {
    private Time startTime;
    private Time stopTime;
    private boolean isRunning;

    /**
     * Constructs a timer with default settings.
     */
    public Timer() {
        this.startTime = Clock.now();
        this.stopTime = Clock.now();
        this.isRunning = false;
    }

    /**
     * Constructs a timer with the provided data.
     *
     * @param startTime The time when the timer was started.
     * @param stopTime The time when the timer was stopped.
     * @param isRunning Whether the timer is running.
     */
    public Timer(Time startTime, Time stopTime, boolean isRunning) {
        this.startTime = startTime;
        this.stopTime = stopTime;
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
        return new Timer(startTime, Clock.now(), false);
    }

    public Time getStartTime() {
        assert (startTime != null);
        return startTime;
    }

    public Time getStopTime() {
        assert (stopTime != null);
        return stopTime;
    }

    /**
     * Returns the length of time tracked in this timer, in (@code unit) units.
     *
     * @param unit The units for the length of time.
     * @return The length of time in the provided units.
     */
    public long getTimeBetween(ChronoUnit unit) {
        assert (!isRunning);
        return unit.between(startTime.getTime(), stopTime.getTime());
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

        return startTime.equals(other.startTime)
                && Objects.equals(startTime, other.startTime)
                && Objects.equals(stopTime, other.stopTime)
                && Objects.equals(isRunning, other.isRunning);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, stopTime, isRunning);
    }
}
