//@@author claracheong4

package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import seedu.momentum.commons.util.TimeUtil;

/**
 * Represents a time in the project book.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class TimeWrapper implements InstanceWrapper<LocalTime>, Comparable<TimeWrapper> {

    public static final String MESSAGE_CONSTRAINTS =
            "Times should be in ISO8601 format. e.g. 10:15:30";

    private final LocalTime time;

    /**
     * Constructs a {@code TimeWrapper}.
     *
     * @param time A valid TimeWrapper.
     */
    public TimeWrapper(String time) {
        requireNonNull(time);
        checkArgument(isValid(time), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(time, TimeUtil.FORMAT_DATA);
    }

    public TimeWrapper(LocalTime time) {
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid time.
     *
     * @param test String to be tested.
     * @return Whether the string is valid.
     */
    public static boolean isValid(String test) {
        try {
            TimeUtil.FORMAT_DATA.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public LocalTime get() {
        return this.time;
    }

    @Override
    public String getFormatted() {
        return this.time.format(TimeUtil.FORMAT_TIME_MEDIUM);
    }

    @Override
    public String toString() {
        return this.time.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeWrapper // instanceof handles nulls
                && this.time.equals(((TimeWrapper) other).get())); // state check
    }

    @Override
    public int hashCode() {
        return this.time.hashCode();
    }

    //@@author kkangs0226
    @Override
    public int compareTo(TimeWrapper other) {
        if (this.get().isBefore(other.get())) {
            return -1;
        } else if (this.get().isAfter(other.get())) {
            return 1;
        } else {
            return 0;
        }
    }

}
