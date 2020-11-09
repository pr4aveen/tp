//@@author claracheong4
package seedu.momentum.model.reminder;

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
import seedu.momentum.commons.exceptions.IllegalValueException;

/**
 * Represents a Project's reminder in the project book.
 * Guarantees: immutable; is valid.
 */
public class Reminder {

    public static final String REMINDER_MESSAGE_CONSTRAINTS =
            "Date and time of reminder cannot be earlier than current time";

    public static final String MESSAGE_CONSTRAINTS = DateTimeWrapper.MESSAGE_CONSTRAINTS + "\n"
            + REMINDER_MESSAGE_CONSTRAINTS;

    private static final String REMINDER_ICON = "\ud83d\udd14";

    private final Optional<DateTimeWrapper> dateTimeWrapper;
    private final boolean expired;

    /**
     * Constructs an empty reminder.
     */
    public Reminder() {
        this.dateTimeWrapper = Optional.empty();
        this.expired = false;
    }

    /**
     * Constructs a {@code Reminder}.
     *
     * @param dateTime A valid dateTimeWrapper after current date and time.
     */
    public Reminder(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(DateTimeWrapper.isValid(dateTime), DateTimeWrapper.MESSAGE_CONSTRAINTS);
        checkArgument(isValid(dateTime), REMINDER_MESSAGE_CONSTRAINTS);
        this.dateTimeWrapper = Optional.of(new DateTimeWrapper(dateTime));
        this.expired = false;
    }

    private Reminder(Optional<DateTimeWrapper> dateTimeWrapper, boolean expired) {
        this.dateTimeWrapper = dateTimeWrapper;
        this.expired = expired;
    }

    /**
     * Checks if a dateTimeWrapper is valid.
     *
     * @param dateTimeStr A string to be parsed as a dateTimeWrapper.
     * @return True if the dateTimeWrapper is after current date and time, false otherwise..
     */
    public static boolean isValid(String dateTimeStr) {
        DateTimeWrapper dateTimeWrapper = new DateTimeWrapper(dateTimeStr);
        return !checkExpiry(dateTimeWrapper);
    }

    /**
     * Recreate a reminder from a dateTimeStr.
     *
     * @param dateTimeStr The date time str.
     * @return The new reminder created with the dateTimeStr.
     * @throws IllegalValueException If the reminder is invalid.
     */
    public static Reminder recreateReminder(String dateTimeStr) throws IllegalValueException {
        if (dateTimeStr == null) {
            return new Reminder();
        }

        if (!DateTimeWrapper.isValid(dateTimeStr)) {
            throw new IllegalValueException(DateTimeWrapper.MESSAGE_CONSTRAINTS);
        }

        DateTimeWrapper dateTime = new DateTimeWrapper(dateTimeStr);
        return new Reminder(Optional.of(dateTime), checkExpiry(dateTime));
    }

    private Instant toInstant() {
        return this.getDateTimeWrapper().get().atZone(ZoneId.systemDefault()).toInstant();
    }

    private static boolean checkExpiry(DateTimeWrapper dateTimeWrapper) {
        return dateTimeWrapper.compareTo(Clock.now()) < 0;
    }

    /**
     * Checks if a reminder is expired.
     *
     * @return True if the reminder is expired, false otherwise.
     */
    public boolean isExpired() {
        return this.expired;
    }

    /**
     * Checks if a reminder is empty.
     *
     * @return True if the reminder is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.dateTimeWrapper.isEmpty();
    }

    /**
     * Checks if a reminder can be scheduled.
     *
     * @return True if the reminder can be scheduled, false otherwise.
     */
    public boolean canSchedule() {
        return !isEmpty() && !isExpired() && !checkExpiry(this.dateTimeWrapper.get());
    }

    /**
     * Gets dateTimeWrapper of a reminder.
     *
     * @return The dateTimeWrapper of the reminder instance.
     * @throws NoSuchElementException If there is no dateTimeWrapper.
     */
    public DateTimeWrapper getDateTimeWrapper() throws NoSuchElementException {
        return this.dateTimeWrapper.get();
    }

    /**
     * Remove the reminder.
     *
     * @return A new reminder with no dateTimeWrapper specified.
     */
    public Reminder remove() {
        return new Reminder();
    }

    /**
     * Returns an expired reminder if expired, else returns the same reminder.
     *
     * @return The updated reminder.
     */
    public Reminder updateExpired() {
        return new Reminder(this.dateTimeWrapper, !canSchedule());
    }

    /**
     * Convert the dateTime in reminder to a date object.
     *
     * @return The converted date object.
     */
    public Date toDate() {
        return Date.from(toInstant());
    }

    /**
     * Gets status of the reminder.
     *
     * @return The status of the reminder.
     */
    public String getStatus() {
        return isEmpty() ? "" : REMINDER_ICON;
    }

    /**
     * Gets formatted reminder.
     *
     * @return The formatted reminder.
     */
    public String getFormattedReminder() {
        return this.dateTimeWrapper.map(dateTime -> dateTime.getFormatted()
                + (expired ? " (missed)" : ""))
                .orElse("No reminder set");
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
}
