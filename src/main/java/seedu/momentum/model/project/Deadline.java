package seedu.momentum.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.TimeWrapper;

/**
 * Represents a deadline in Momentum.
 * Guarantees: immutable; is valid.
 */
public class Deadline implements Comparable<Deadline> {

    /**
     * The constant CREATED_DATE_MESSAGE_CONSTRAINT.
     */
    public static final String CREATED_DATE_MESSAGE_CONSTRAINT = "Date of deadline cannot be earlier than created date";
    /**
     * The constant MESSAGE_CONSTRAINTS.
     */
    public static final String MESSAGE_CONSTRAINTS = DateWrapper.MESSAGE_CONSTRAINTS + "\n"
            + TimeWrapper.MESSAGE_CONSTRAINTS + "\n"
            + CREATED_DATE_MESSAGE_CONSTRAINT;
    private final Optional<DateWrapper> dateWrapper;
    private final Optional<TimeWrapper> timeWrapper;

    /**
     * Constructs an empty deadline.
     */
    public Deadline() {
        this.dateWrapper = Optional.empty();
        this.timeWrapper = Optional.empty();
    }

    /**
     * Constructs a {@code Deadline}.
     *
     * @param dateWrapper        A valid dateWrapper after or on created dateWrapper.
     * @param createdDateWrapper A created dateWrapper.
     */
    public Deadline(String dateWrapper, DateWrapper createdDateWrapper) {
        requireAllNonNull(dateWrapper, createdDateWrapper);
        checkArgument(DateWrapper.isValid(dateWrapper), DateWrapper.MESSAGE_CONSTRAINTS);
        checkArgument(!isBeforeCreatedDate(dateWrapper, createdDateWrapper), CREATED_DATE_MESSAGE_CONSTRAINT);
        this.dateWrapper = Optional.of(new DateWrapper(dateWrapper));
        this.timeWrapper = Optional.empty();
    }

    /**
     * Constructs a {@code Deadline}.
     *
     * @param dateWrapper        A valid dateWrapper after or on created dateWrapper.
     * @param timeWrapper        A valid timeWrapper.
     * @param createdDateWrapper A created dateWrapper.
     */
    public Deadline(String dateWrapper, String timeWrapper, DateWrapper createdDateWrapper) {
        requireNonNull(dateWrapper, timeWrapper);
        requireNonNull(createdDateWrapper);
        checkArgument(DateWrapper.isValid(dateWrapper), DateWrapper.MESSAGE_CONSTRAINTS);
        checkArgument(TimeWrapper.isValid(timeWrapper), TimeWrapper.MESSAGE_CONSTRAINTS);
        checkArgument(!isBeforeCreatedDate(dateWrapper, createdDateWrapper), CREATED_DATE_MESSAGE_CONSTRAINT);
        this.dateWrapper = Optional.of(new DateWrapper(dateWrapper));
        this.timeWrapper = Optional.of(new TimeWrapper(timeWrapper));
    }

    /**
     * Returns true if the dateWrapper is after or on the created dateWrapper, false otherwise.
     *
     * @param dateStr     A string to be parsed as a dateWrapper.
     * @param createdDateWrapper A created dateWrapper.
     * @return the isBeforeCreatedDate boolean
     */
    public static boolean isBeforeCreatedDate(String dateStr, DateWrapper createdDateWrapper) {
        DateWrapper dateWrapper = new DateWrapper(dateStr);
        return dateWrapper.compareTo(createdDateWrapper) < 0;
    }

    /**
     * Returns true if the deadline is empty, false otherwise.
     *
     * @return the isEmpty boolean
     */
    public boolean isEmpty() {
        return this.dateWrapper.isEmpty();
    }

    /**
     * Gets dateWrapper of a deadline.
     *
     * @return the dateWrapper
     * @throws NoSuchElementException If there is no dateWrapper.
     */
    public DateWrapper getDate() throws NoSuchElementException {
        return this.dateWrapper.get();
    }

    /**
     * Returns true if the deadline has a timeWrapper, false otherwise.
     *
     * @return the hasTime boolean
     */
    public boolean hasTime() {
        return this.timeWrapper.isPresent();
    }

    /**
     * Gets timeWrapper of a deadline.
     *
     * @return the timeWrapper
     * @throws NoSuchElementException If there is no timeWrapper.
     */
    public TimeWrapper getTime() throws NoSuchElementException {
        return this.timeWrapper.get();
    }

    /**
     * Gets formatted deadline.
     *
     * @return the formatted deadline
     */
    public String getFormattedDeadline() {
        return isEmpty() ? "No deadline set"
                : this.dateWrapper.map(DateWrapper::getFormatted).orElse("")
                + this.timeWrapper.map(timeWrapper -> " " + timeWrapper.getFormatted()).orElse("");
    }

    /**
     * Gets the number of days to the dateWrapper of the deadline, from the current timeWrapper.
     *
     * @return Number of days to deadline.
     */
    public long daysToDeadline() {
        return DateWrapper.getTimeBetween(Clock.now().getDateWrapper(), getDate(), ChronoUnit.DAYS);
    }

    @Override
    public String toString() {
        return this.dateWrapper.map(DateWrapper::toString).orElse("")
                + this.timeWrapper.map(TimeWrapper::toString).orElse("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.dateWrapper.equals(((Deadline) other).dateWrapper)
                && this.timeWrapper.equals(((Deadline) other).timeWrapper)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dateWrapper, this.timeWrapper);
    }

    @Override
    public int compareTo(Deadline other) {
        DateWrapper thisDateWrapper = this.getDate();
        DateWrapper otherDateWrapper = other.getDate();

        if (thisDateWrapper.get().isBefore(otherDateWrapper.get())) {
            return -1;
        } else if (thisDateWrapper.get().isAfter(otherDateWrapper.get())) {
            return 1;
        } else {
            return sameDateCompare(other);
        }
    }

    /**
     * Compares timeWrapper of two deadlines with same dateWrapper.
     *
     * @param other other deadline.
     * @return integer to indicate order.
     */
    private int sameDateCompare(Deadline other) {
        if (!this.hasTime() && other.hasTime()) {
            return -1;
        } else if (this.hasTime() && !other.hasTime()) {
            return 1;
        } else if (this.hasTime() && other.hasTime()) {
            TimeWrapper thisTimeWrapper = this.getTime();
            TimeWrapper otherTimeWrapper = other.getTime();
            return thisTimeWrapper.compareTo(otherTimeWrapper);
        } else {
            return 0;
        }
    }

}
