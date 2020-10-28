package seedu.momentum.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the {@code StatisticTimeframe} settings.
 * Guarantees: immutable.
 */
public class StatisticTimeframeSettings implements Serializable {

    private static final StatisticTimeframe DEFAULT_TIMEFRAME =
        new StatisticTimeframe(StatisticTimeframe.Timeframe.WEEKLY);

    private final StatisticTimeframe statTimeframe;

    /**
     * Constructs a {@code StatisticTimeframeSettings} with the default timeframe.
     */
    public StatisticTimeframeSettings() {
        statTimeframe = DEFAULT_TIMEFRAME;
    }

    /**
     * Constructs a {@code StatisticTimeframeSettings} with the specified timeframe.
     */
    public StatisticTimeframeSettings(StatisticTimeframe statTimeframe) {
        this.statTimeframe = statTimeframe;
    }

    public StatisticTimeframe getStatTimeframe() {
        return statTimeframe;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof StatisticTimeframeSettings)) { //this handles null as well.
            return false;
        }

        StatisticTimeframeSettings o = (StatisticTimeframeSettings) other;

        return statTimeframe.equals(o.statTimeframe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statTimeframe);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Timeframe : " + statTimeframe + "\n");
        return sb.toString();
    }
}
