package seedu.momentum.commons.core;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

/**
 * A Serializable class that contains the {@code Statistic} timeframe settings.
 * Guarantees: immutable.
 */
public class StatisticTimeframe implements Serializable {

    private static final ChronoUnit DEFAULT_TIMEFRAME = ChronoUnit.WEEKS;
    private static final ChronoUnit[] ACCEPTED_TIMEFRAMES =
        new ChronoUnit[] {ChronoUnit.DAYS, ChronoUnit.WEEKS, ChronoUnit.MONTHS};

    private final ChronoUnit timeframe;

    /**
     * Constructs a {@code StatisticTimeframe} with the default timeframe.
     */
    public StatisticTimeframe() {
        timeframe = DEFAULT_TIMEFRAME;
    }

    /**
     * Constructs a {@code StatisticTimeframe} with the specified timeframe.
     */
    public StatisticTimeframe(ChronoUnit timeframe) {
        assert isValidTimeframe(timeframe);
        this.timeframe = timeframe;
    }

    /**
     * Checks whether the given {@code unit} is an accepted timeframe.
     */
    public boolean isValidTimeframe(ChronoUnit unit) {
        return Arrays.asList(ACCEPTED_TIMEFRAMES).contains(unit);
    }

    public ChronoUnit getTimeframe() {
        return timeframe;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Timeframe : " + timeframe + "\n");
        return sb.toString();
    }
}
