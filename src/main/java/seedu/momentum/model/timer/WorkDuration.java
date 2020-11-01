package seedu.momentum.model.timer;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.UniqueItem;

/**
 * Represents a duration of dateTimeWrapper spent working on a project.
 * Guarantees: immutable.
 */
public class WorkDuration implements UniqueItem<WorkDuration> {
    private final DateTimeWrapper startDateTime;
    private final DateTimeWrapper stopDateTime;

    /**
     * Constructs a {@code WorkDuration}.
     *
     * @param startDateTime A valid start dateTimeWrapper.
     * @param stopDateTime A valid stop dateTimeWrapper.
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
     * Returns the length of dateTimeWrapper tracked in this duration, in (@code unit) units.
     *
     * @param unit The units for the length of dateTimeWrapper.
     * @return The length of dateTimeWrapper in the provided units.
     */
    public long getTimeBetween(ChronoUnit unit) {
        return unit.between(startDateTime.get(), stopDateTime.get());
    }

    /**
     * Returns true if both durations have the same start and stop dateTimeWrapper.
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
