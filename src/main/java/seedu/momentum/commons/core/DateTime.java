package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import seedu.momentum.commons.util.DateTimeUtil;

/**
 * Represents a WorkDuration's dateTime in the project book.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class DateTime implements Instance<LocalDateTime> {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates and Times should be in ISO8601 format. e.g. 2020-09-23T16:55:12.83012";

    private final LocalDateTime dateTime;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid DateTime.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValid(dateTime), MESSAGE_CONSTRAINTS);
        this.dateTime = LocalDateTime.parse(dateTime, DateTimeUtil.FORMAT_DATA);
    }

    public DateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Returns a new DateTime that is after this DateTime be a specified amount
     *
     * @param amount Amount to increase by.
     * @param unit   Unit to increase with.
     * @return The new dateTime
     */
    public DateTime plus(long amount, ChronoUnit unit) {
        return new DateTime(dateTime.plus(amount, unit));
    }

    /**
     * Returns a new DateTime that is before this DateTime by a specified amount.
     *
     * @param amount Amount to decrease by.
     * @param unit   Unit to decrease with.
     * @return The new dateTime.
     */
    public DateTime minus(long amount, ChronoUnit unit) {
        return new DateTime(dateTime.minus(amount, unit));
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValid(String test) {
        try {
            DateTimeUtil.FORMAT_DATA.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public LocalDateTime get() {
        return dateTime;
    }

    @Override
    public String getFormatted() {
        return dateTime.format(DateTimeUtil.FORMAT_DATE_TIME_MEDIUM);
    }

    @Override
    public String toString() {
        return this.dateTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && dateTime.equals(((DateTime) other).get())); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

}
