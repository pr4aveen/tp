package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.momentum.commons.util.DateUtil;

/**
 * Represents a date in the project book.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Date implements Instance<LocalDate> {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in ISO8601 format. e.g. 2011-12-03";

    private final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid Date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValid(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date, DateUtil.FORMAT_DATA);
    }

    public Date(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValid(String test) {
        try {
            DateUtil.FORMAT_DATA.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
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
                || (other instanceof Date // instanceof handles nulls
                && this.date.equals(((Date) other).get())); // state check
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

}
