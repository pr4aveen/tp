package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.momentum.commons.util.DateUtil;

/**
 * Represents a date in the project book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

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
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date, DateUtil.FORMAT_DATA);
    }

    public Date(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            DateUtil.FORMAT_DATA.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getFormattedDate() {
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
                && this.date.equals(((Date) other).getDate())); // state check
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

}
