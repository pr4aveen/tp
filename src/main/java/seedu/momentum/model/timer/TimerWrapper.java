package seedu.momentum.model.timer;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTimeWrapper;

/**
 * Represents a timerWrapper in momentum.
 * Guarantees: immutable.
 */
public class TimerWrapper {
    private DateTimeWrapper startDateTime;
    private DateTimeWrapper stopDateTime;
    private boolean isRunning;

    /**
     * Constructs a timerWrapper with default settings.
     */
    public TimerWrapper() {
        this.startDateTime = Clock.now();
        this.stopDateTime = Clock.now();
        this.isRunning = false;
    }

    /**
     * Constructs a timerWrapper with the provided data.
     *
     * @param startDateTime The dateTimeWrapper when the timerWrapper was started.
     * @param stopDateTime The dateTimeWrapper when the timerWrapper was stopped.
     * @param isRunning Whether the timerWrapper is running.
     */
    public TimerWrapper(DateTimeWrapper startDateTime, DateTimeWrapper stopDateTime, boolean isRunning) {
        this.startDateTime = startDateTime;
        this.stopDateTime = stopDateTime;
        this.isRunning = isRunning;
    }

    /**
     * Start the timerWrapper.
     */
    public TimerWrapper start() {
        assert (!isRunning);
        return new TimerWrapper(Clock.now(), Clock.now(), true);
    }

    /**
     * Stop the timerWrapper.
     */
    public TimerWrapper stop() {
        assert (isRunning);
        return new TimerWrapper(startDateTime, Clock.now(), false);
    }

    public DateTimeWrapper getStartTime() {
        assert (startDateTime != null);
        return startDateTime;
    }

    public DateTimeWrapper getStopTime() {
        assert (stopDateTime != null);
        return stopDateTime;
    }

    /**
     * Returns the length of dateTimeWrapper tracked in this timerWrapper, in (@code unit) units.
     *
     * @param unit The units for the length of dateTimeWrapper.
     * @return The length of dateTimeWrapper in the provided units.
     */
    public long getTimeBetween(ChronoUnit unit) {
        assert (!isRunning);
        return unit.between(startDateTime.get(), stopDateTime.get());
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
        TimerWrapper other = (TimerWrapper) o;

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
