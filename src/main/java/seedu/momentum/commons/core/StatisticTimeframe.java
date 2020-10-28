package seedu.momentum.commons.core;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Timeframe of statistics tracked.
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
     * Converts the timeframe to a {@code ChronoUnit}.
     */
    public ChronoUnit toChronoUnit() {
        return timeframe == Timeframe.DAILY
            ? ChronoUnit.DAYS
            : timeframe == Timeframe.WEEKLY
            ? ChronoUnit.WEEKS
            : ChronoUnit.MONTHS;
    }

    @Override
    public String toString() {
        return timeframe == Timeframe.DAILY
            ? "Daily"
            : timeframe == Timeframe.WEEKLY
            ? "Weekly"
            : "Monthly";
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
    public int hashCode() {
        return Objects.hash(timeframe);
    }

    public enum Timeframe {
        DAILY, WEEKLY, MONTHLY
    }
}
