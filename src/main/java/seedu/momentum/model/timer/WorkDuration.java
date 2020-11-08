//@@author boundtotheearth
package seedu.momentum.model.timer;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.UniqueItem;

/**
 * Represents a duration of time spent working on a project.
 * Guarantees: immutable.
 */
public class WorkDuration implements UniqueItem<WorkDuration> {
    private final DateTimeWrapper startDateTime;
    private final DateTimeWrapper stopDateTime;

    /**
     * Constructs a {@code WorkDuration}.
     *
     * @param startDateTime A valid start time.
     * @param stopDateTime A valid stop time.
     */
    public WorkDuration(DateTimeWrapper startDateTime, DateTimeWrapper stopDateTime) {
        this.startDateTime = startDateTime;
        this.stopDateTime = stopDateTime;
    }

    public DateTimeWrapper getStartTime() {
        return startDateTime;
    }

    public DateTimeWrapper getStopTime() {
        return stopDateTime;
    }

    /**
     * Returns the length of time tracked in this duration, in {@code unit} units.
     *
     * @param unit The units for the length of time.
     * @return The length of time in the provided units.
     */
    public long getTimeBetween(ChronoUnit unit) {
        return unit.between(startDateTime.get(), stopDateTime.get());
    }

    /**
     * Checks if two durations are the same. It works the same as {@link #equals}, and does not define a weaker notion
     * of equality between two {@code WorkDurations}.
     *
     * @param otherDuration Other duration to check against.
     * @return True if both durations have the same start and stop time, false otherwise.
     */
    @Override
    public boolean isSameAs(WorkDuration otherDuration) {
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
                && isSameAs((WorkDuration) other)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, stopDateTime);
    }
}
