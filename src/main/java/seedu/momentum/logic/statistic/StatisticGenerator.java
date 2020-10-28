package seedu.momentum.logic.statistic;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.StatisticTimeframe;

/**
 * API of a statistics generator
 */
public interface StatisticGenerator {

    /**
     * Recalculates all the statistics being tracked.
     */
    void updateStatistics();

    /**
     * Updates the timeframe of the statistics tracked.
     */
    void updateStatisticTimeframe(StatisticTimeframe timeframe);

    /**
     * Returns a list containing the amount of time spent on each project in the past week.
     *
     * @return A list of StatisticEntry, each entry containing the project name and time spent.
     */
    ObservableList<StatisticEntry> getTimePerProjectStatistic();
}
