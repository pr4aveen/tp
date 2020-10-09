package seedu.momentum.model.timer;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Represents a WorkDuration's time in the project book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Times should be in ISO8601 format. e.g. 2020-09-23T16:55:12.83012";

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
     * Returns a new Time that is after this Time be a specified amount
     *
     * @param amount Amount to increase by.
     * @param unit Unit to increase with.
     * @return The new time
     */
    public Time plus(long amount, ChronoUnit unit) {
        return new Time(time.plus(amount, unit));
    }

    /**
     * Returns a new Time that is before this Time by a specified amount.
     *
     * @param amount Amount to decrease by.
     * @param unit Unit to decrease with.
     * @return The new time
     */
    public Time minus(long amount, ChronoUnit unit) {
        return new Time(time.minus(amount, unit));
    }

    public boolean isBefore(Time otherTime) {
        return time.isBefore(otherTime.getTime());
    }

    public boolean isAfter(Time otherTime) {
        return time.isAfter(otherTime.getTime());
    }

    public boolean isEqual(Time otherTime) {
        return time.isEqual(otherTime.getTime());
    }

    public static long getTimeBetween(Time time1, Time time2, ChronoUnit units) {
        return units.between(time1.getTime(), time2.getTime());
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
