//@@author khoodehui

package seedu.momentum.commons.core;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents the timeframe for which statistics are tracked.
 */
public class StatisticTimeframe implements Serializable {

    public static final String MESSAGE_CONSTRAINTS =
            "Timeframe should be either 'daily', 'weekly', or 'monthly'.";

    private Timeframe timeframe;

    /**
     * Empty constructor required by Jackson.
     */
    public StatisticTimeframe() {
    }

    /**
     * Constructs a {@code StatisticTimeframe} with the specified timeframe.
     */
    public StatisticTimeframe(Timeframe timeframe) {
        this.timeframe = timeframe;
    }

    /**
     * Constructs a {@code StatisticTimeframe} with the specified timeframe expressed as a String.
     * <p>
     *
     * @param timeframe a valid timeframe.
     * @param timeframe A valid timeframe.
     */
    public StatisticTimeframe(String timeframe) {
        requireNonNull(timeframe);
        checkArgument(isValid(timeframe), MESSAGE_CONSTRAINTS);
        this.timeframe = Timeframe.valueOf(timeframe.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid timeframe.
     *
     * @param timeframe The timeframe to be tested.
     * @returns Whether the timeframe is valid.
     */
    public static boolean isValid(String timeframe) {
        try {
            Timeframe.valueOf(timeframe.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Converts the timeframe to a {@code ChronoUnit}.
     *
     * @returns The ChronoUnit associated with the timeframe.
     */
    public ChronoUnit toChronoUnit() {
        return timeframe.toChronoUnit();
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeframe);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof StatisticTimeframe)) { //this handles null as well.
            return false;
        }

        StatisticTimeframe o = (StatisticTimeframe) other;

        return timeframe == o.timeframe;
    }

    @Override
    public String toString() {
        return timeframe.toString();
    }

    /**
     * Represents the possible timeframes supported by Momentum.
     */
    public enum Timeframe {
        DAILY,
        WEEKLY,
        MONTHLY;

        /**
         * Converts the timeframe to a {@code ChronoUnit}.
         *
         * @return The ChronoUnit associated with the timeframe.
         */
        public ChronoUnit toChronoUnit() {
            return this == DAILY
                    ? ChronoUnit.DAYS
                    : this == WEEKLY
                    ? ChronoUnit.WEEKS
                    : ChronoUnit.MONTHS;
        }

        @Override
        public String toString() {
            return super.toString().charAt(0)
                    + super.toString().substring(1).toLowerCase();
        }
    }
}
