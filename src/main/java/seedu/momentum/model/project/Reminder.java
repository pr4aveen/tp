package seedu.momentum.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.DateWrapper;

/**
 * Represents a Project's reminder in the project book.
 * Guarantees: immutable; is valid
 */
public class Reminder implements Comparable<Reminder> {
    /**
     * The constant CREATED_DATE_MESSAGE_CONSTRAINT.
     */
    public static final String CREATED_DATE_MESSAGE_CONSTRAINT = "Date of reminder cannot be earlier than created date";
    /**
     * The constant CREATED_DATE_MESSAGE_CONSTRAINT.
     */
    public static final String REMINDER_MESSAGE_CONSTRAINTS =
            "Date and time of reminder cannot be earlier than current time";
    /**
     * The constant MESSAGE_CONSTRAINTS.
     */
    public static final String MESSAGE_CONSTRAINTS = DateTimeWrapper.MESSAGE_CONSTRAINTS + "\n"
            + CREATED_DATE_MESSAGE_CONSTRAINT + REMINDER_MESSAGE_CONSTRAINTS;

    private static final String REMINDER_ICON = "\ud83d\udd14";

    private final Optional<DateTimeWrapper> dateTimeWrapper;

    /**
     * Constructs an empty reminder.
     */
    public Reminder() {
        this.dateTimeWrapper = Optional.empty();
    }

    /**
     * Constructs a {@code Reminder}.
     *
     * @param dateTime           A valid dateTimeWrapper after or on created dateTimeWrapper.
     * @param createdDateWrapper A created dateWrapper.
     */
    public Reminder(String dateTime, DateWrapper createdDateWrapper) {
        requireNonNull(dateTime);
        checkArgument(DateTimeWrapper.isValid(dateTime), DateTimeWrapper.MESSAGE_CONSTRAINTS);
        checkArgument(isValid(dateTime), REMINDER_MESSAGE_CONSTRAINTS);
        checkArgument(!isBeforeCreatedDate(dateTime, createdDateWrapper), CREATED_DATE_MESSAGE_CONSTRAINT);
        this.dateTimeWrapper = Optional.of(new DateTimeWrapper(dateTime));
    }

    /**
     * Returns true if the dateTimeWrapper is after or on the current date and time, false otherwise.
     *
     * @param dateTimeStr A string to be parsed as a dateTimeWrapper.
     * @return the isValid boolean
     */
    public static boolean isValid(String dateTimeStr) {
        DateTimeWrapper dateTimeWrapper = new DateTimeWrapper(dateTimeStr);
        return dateTimeWrapper.compareTo(Clock.now()) > 0;
    }

    /**
     * Returns true if the dateTimeWrapper is after or on the created dateWrapper, false otherwise.
     *
     * @param dateTimeStr        A string to be parsed as a dateTimeWrapper.
     * @param createdDateWrapper A created dateWrapper.
     * @return the isBeforeCreatedDate boolean
     */
    public static boolean isBeforeCreatedDate(String dateTimeStr, DateWrapper createdDateWrapper) {
        DateTimeWrapper dateTimeWrapper = new DateTimeWrapper(dateTimeStr);
        return dateTimeWrapper.getDateWrapper().compareTo(createdDateWrapper) < 0;
    }

    /**
     * Returns true if the reminder is empty, false otherwise.
     *
     * @return the isEmpty boolean
     */
    public boolean isEmpty() {
        return this.dateTimeWrapper.isEmpty();
    }

    /**
     * Gets dateTimeWrapper of a reminder.
     *
     * @return the dateTimeWrapper
     * @throws NoSuchElementException If there is no dateTimeWrapper.
     */
    public DateTimeWrapper getDateTimeWrapper() throws NoSuchElementException {
        return this.dateTimeWrapper.get();
    }

    /**
     * Gets status of the reminder.
     *
     * @return the status of the reminder.
     */
    public String getStatus() {
        return isEmpty() ? "" : REMINDER_ICON;
    }

    /**
     * Gets formatted reminder.
     *
     * @return the formatted reminder.
     */
    public String getFormattedReminder() {
        return this.dateTimeWrapper.map(DateTimeWrapper::getFormatted).orElse("No reminder set");
    }

    /**
     * Remove the reminder.
     *
     * @return the new reminder.
     */
    public Reminder remove() {
        return new Reminder();
    }

    private Instant toInstant() {
        return this.getDateTimeWrapper().get().atZone(ZoneId.systemDefault()).toInstant();
    }

    /**
     * Convert the dateTime in reminder to a date object.
     *
     * @return the date object.
     */
    public Date toDate() {
        return Date.from(toInstant());
    }

    @Override
    public String toString() {
        return this.dateTimeWrapper.map(DateTimeWrapper::toString).orElse("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reminder // instanceof handles nulls
                && this.dateTimeWrapper.equals(((Reminder) other).dateTimeWrapper)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dateTimeWrapper);
    }

    @Override
    public int compareTo(Reminder other) {
        if (this.isEmpty() && other.isEmpty()) {
            return 0;
        } else if (this.isEmpty() && !other.isEmpty()) {
            return 1;
        } else if (!this.isEmpty() && other.isEmpty()) {
            return -1;
        } else {
            assert !this.isEmpty() && !other.isEmpty();
            return this.dateTimeWrapper.get().compareTo(other.dateTimeWrapper.get());
        }
    }
}
