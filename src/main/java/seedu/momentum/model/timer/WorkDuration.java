package seedu.momentum.model.timer;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.momentum.commons.core.DateTime;

/**
 * Represents a duration of dateTime spent working on a project.
 * Guarantees: immutable.
 */
public class WorkDuration {
    private final DateTime startDateTime;
    private final DateTime stopDateTime;

    /**
     * Constructs a {@code WorkDuration}.
     *
     * @param startDateTime A valid start dateTime.
     * @param stopDateTime A valid stop dateTime.
     */
    public WorkDuration(DateTime startDateTime, DateTime stopDateTime) {
        this.startDateTime = startDateTime;
        this.stopDateTime = stopDateTime;
    }

    public DateTime getStartTime() {
        return startDateTime;
    }

    public DateTime getStopTime() {
        return stopDateTime;
    }

    /**
     * Returns the length of dateTime tracked in this duration, in (@code unit) units.
     *
     * @param unit The units for the length of dateTime.
     * @return The length of dateTime in the provided units.
     */
    public long getTimeBetween(ChronoUnit unit) {
        return unit.between(startDateTime.getDateTime(), stopDateTime.getDateTime());
    }

    /**
     * Returns true if both durations have the same start and stop dateTime.
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
        return Objects.hash(startDateTime, stopDateTime);
    }
}
