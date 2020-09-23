package seedu.momentum.model.timer;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Represents a WorkDuration's time in the project book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Times should be in ISO8601 format";

    private final LocalDateTime time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid Time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalDateTime.parse(time, DurationUtil.FORMAT_DATA);
    }

    public Time(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        try {
            DurationUtil.FORMAT_DATA.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time.format(DurationUtil.FORMAT_DATE_TIME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).getTime())); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

}
