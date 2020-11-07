//@@author boundtotheearth

package seedu.momentum.logic.statistic;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.model.Model;

/**
 * Manages all the statistics being tracked in Momentum.
 * This class contains the specifications of statistics being tracked by the app,
 * and methods to generate those statistics.
 */
public class StatisticManager implements StatisticGenerator {
    private static final Logger LOGGER = LogsCenter.getLogger(StatisticManager.class);

    private Model model;

    // Statistics being tracked by the app
    private Statistic totalTimePerProjectStatistic;

    // Maintain an array of the above statistics for easy iteration
    private Statistic[] statistics;

    /**
     * Constructs a {@code StatisticManager} that tracks statistics form the specified model.
     *
     * @param model The model containing the data to track.
     */
    public StatisticManager(Model model) {
        LOGGER.info("Initialized Statistic Manager");
        this.model = model;
        totalTimePerProjectStatistic = new PeriodicTotalTimeStatistic(
            model.getStatisticTimeframeSettings().getStatTimeframe(), ChronoUnit.MINUTES);
        statistics = new Statistic[] { totalTimePerProjectStatistic };
        updateStatistics();
    }

    /**
     * Constructs a {@code StatisticManager} with a specified model and statistics data.
     *
     * @param model The Model to track.
     * @param statistics The data to set.
     */
    public StatisticManager(Model model, Statistic[] statistics) {
        this.model = model;
        this.statistics = statistics;
    }

    @Override
    public void updateStatistics() {
        LOGGER.info("Updated Statistics");
        for (Statistic statistic : statistics) {
            statistic.calculate(model);
        }
    }

    //@@author khoodehui
    @Override
    public void updateStatisticTimeframe(StatisticTimeframe timeframe) {
        totalTimePerProjectStatistic.setTimeframe(timeframe);
    }
    //@@author

    @Override
    public ObservableList<StatisticEntry> getTimePerProjectStatistic() {
        LOGGER.info("Time Per Project Statistic Retrieved");
        return totalTimePerProjectStatistic.getDisplayList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StatisticManager that = (StatisticManager) o;

        return Objects.equals(model, that.model)
                && Arrays.equals(statistics, that.statistics);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(model, totalTimePerProjectStatistic);
        result = 31 * result + Arrays.hashCode(statistics);
        return result;
    }
}
