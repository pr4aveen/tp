package seedu.momentum.model.timer;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents a duration of time spent working on a project.
 *  * Guarantees: immutable.
 */
public class WorkDuration {
    private final Time startTime;
    private final Time stopTime;

    /**
     * Constructs a {@code WorkDuration}.
     *
     * @param startTime A valid start time.
     * @param stopTime A valid stop time.
     */
    public WorkDuration(Time startTime, Time stopTime) {
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getStopTime() {
        return stopTime;
    }

    /**
     * Returns the length of time tracked in this duration, in (@code unit) units.
     * @param unit The units for the length of time.
     * @return The length of time in the provided units.
     */
    public long getTimeBetween(ChronoUnit unit) {
        return unit.between(startTime.getTime(), stopTime.getTime());
    }

    /**
     * Returns true if both durations have the same start and stop time.
     */
    public boolean isSameDuration(WorkDuration otherDuration) {
        if (otherDuration == this) {
            return true;
        }

        return otherDuration != null
                && otherDuration.getStartTime().equals(getStartTime())
                && otherDuration.getStopTime().equals(getStopTime());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkDuration // instanceof handles nulls
                && isSameDuration((WorkDuration) other)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, stopTime);
    }
}
