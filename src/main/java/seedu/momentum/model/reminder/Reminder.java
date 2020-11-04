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
 * Guarantees: immutable; is valid
 */
public class Reminder {

    /**
     * The constant REMINDER_MESSAGE_CONSTRAINTS.
     */
    public static final String REMINDER_MESSAGE_CONSTRAINTS =
            "Date and time of reminder cannot be earlier than current time";
    /**
     * The constant MESSAGE_CONSTRAINTS.
     */
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
     * Returns true if the dateTimeWrapper is after current date and time, false otherwise.
     *
     * @param dateTimeStr A string to be parsed as a dateTimeWrapper.
     * @return the isValid boolean.
     */
    public static boolean isValid(String dateTimeStr) {
        DateTimeWrapper dateTimeWrapper = new DateTimeWrapper(dateTimeStr);
        return !checkExpiry(dateTimeWrapper);
    }
    
    /**
     * Recreate a reminder from a dateTimeStr.
     *
     * @param dateTimeStr the date time str.
     * @return the reminder created.
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
     * Returns true if the reminder is expired, false otherwise.
     *
     * @return the isExpired boolean
     */
    public boolean isExpired() {
        return this.expired;
    }
    
    /**
     * Returns true if the reminder is empty, false otherwise.
     *
     * @return the isEmpty boolean.
     */
    public boolean isEmpty() {
        return this.dateTimeWrapper.isEmpty();
    }

    /**
     * Returns true if the reminder can be scheduled, false otherwise.
     *
     * @return the canSchedule boolean
     */
    public boolean canSchedule() {
        return !isEmpty() && !isExpired() && !checkExpiry(this.dateTimeWrapper.get());
    }
    
    /**
     * Gets dateTimeWrapper of a reminder.
     *
     * @return the dateTimeWrapper.
     * @throws NoSuchElementException If there is no dateTimeWrapper.
     */
    public DateTimeWrapper getDateTimeWrapper() throws NoSuchElementException {
        return this.dateTimeWrapper.get();
    }

    /**
     * Remove the reminder.
     *
     * @return the new reminder.
     */
    public Reminder remove() {
        return new Reminder();
    }

    /**
     * Returns an expired reminder if expired, else returns the same reminder.
     *
     * @return the new reminder.
     */
    public Reminder updateExpired() {
        return new Reminder(this.dateTimeWrapper, 
            !isEmpty() && checkExpiry(this.dateTimeWrapper.get()));
    }
    
    /**
     * Convert the dateTime in reminder to a date object.
     *
     * @return the date object.
     */
    public Date toDate() {
        return Date.from(toInstant());
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
