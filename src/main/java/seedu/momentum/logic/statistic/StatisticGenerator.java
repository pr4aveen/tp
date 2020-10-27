package seedu.momentum.logic.statistic;

import javafx.collections.ObservableList;

/**
 * API of a statistics generator
 */
public interface StatisticGenerator {

    /**
     * Recalculates all the statistics being tracked.
     */
    void updateStatistics();

    /**
     * Returns a list containing the amount of time spent on each project in the past week.
     *
     * @return A list of StatisticEntry, each entry containing the project name and time spent.
     */
    ObservableList<StatisticEntry> getTimePerProjectStatistic();
}
