package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import seedu.momentum.commons.util.TimeUtil;

/**
 * Represents a time in the project book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Times should be in ISO8601 format. e.g. 10:15:30";

    private final LocalTime time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid Time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(time, TimeUtil.FORMAT_DATA);
    }

    public Time(LocalTime time) {
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        try {
            TimeUtil.FORMAT_DATA.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public String getFormattedTime() {
        return this.time.format(TimeUtil.FORMAT_TIME_MEDIUM);
    }

    @Override
    public String toString() {
        return this.time.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.time.equals(((Time) other).getTime())); // state check
    }

    @Override
    public int hashCode() {
        return this.time.hashCode();
    }

}
