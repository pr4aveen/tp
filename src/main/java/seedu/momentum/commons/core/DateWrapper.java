//@@author claracheong4

package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import seedu.momentum.commons.util.DateUtil;

/**
 * Represents a date in the project book.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class DateWrapper implements InstanceWrapper<LocalDate>, Comparable<DateWrapper> {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in ISO8601 format. e.g. 2011-12-03";

    public static final DateWrapper MIN = new DateWrapper(LocalDate.EPOCH);

    private final LocalDate date;

    /**
     * Constructs a {@code DateWrapper}.
     *
     * @param date A valid DateWrapper.
     */
    public DateWrapper(String date) {
        requireNonNull(date);
        checkArgument(isValid(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date, DateUtil.FORMAT_DATA);
    }

    public DateWrapper(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     *
     * @param test String to test.
     */
    public static boolean isValid(String test) {
        try {
            DateUtil.FORMAT_DATA.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Calculates the amount of timeWrapper between two instances of timeWrapper, in the provided units.
     *
     * @param time1 The earlier instance of timeWrapper.
     * @param time2 The later instance of timeWrapper.
     * @param units The units to the timeWrapper.
     * @return      The amount of time between the 2 instances, in the units provided.
     */
    public static long getTimeBetween(DateWrapper time1, DateWrapper time2, ChronoUnit units) {
        return units.between(time1.get(), time2.get());
    }

    @Override
    public LocalDate get() {
        return this.date;
    }

    @Override
    public String getFormatted() {
        return this.date.format(DateUtil.FORMAT_DATE_MEDIUM);
    }

    @Override
    public String toString() {
        return this.date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateWrapper // instanceof handles nulls
                && this.date.equals(((DateWrapper) other).get())); // state check
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

    @Override
    public int compareTo(DateWrapper other) {
        if (this.get().isBefore(other.get())) {
            return -1;
        } else if (this.get().isAfter(other.get())) {
            return 1;
        } else {
            return 0;
        }
    }
}
