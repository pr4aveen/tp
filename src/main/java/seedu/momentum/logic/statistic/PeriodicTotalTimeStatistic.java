package seedu.momentum.logic.statistic;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTime;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Tracks the total time spent on each project for a specific timeframe.
 */
public class PeriodicTotalTimeStatistic extends Statistic {

    private StatisticTimeframe timeframe;
    private ChronoUnit units;

    private ObservableList<StatisticEntry> timeList = FXCollections.observableArrayList();

    /**
     * Constructs a {@code PeriodicTotalTimeStatistic}
     *
     * @param timeframe Timeframe to track.
     * @param units Units for the total time calculated.
     */
    public PeriodicTotalTimeStatistic(StatisticTimeframe timeframe, ChronoUnit units) {
        requireAllNonNull(timeframe, units);
        this.timeframe = timeframe;
        this.units = units;
    }

    /**
     * Constructs a {@code PeriodicTotalTimeStatistic} with specified data.
     *
     * @param timeframe Timeframe to track.
     * @param units Units for the total time calculated.
     * @param timeList Data for this statistic.
     */
    public PeriodicTotalTimeStatistic(StatisticTimeframe timeframe,
                                      ChronoUnit units,
                                      ObservableList<StatisticEntry> timeList) {
        requireAllNonNull(timeframe, units, timeList);
        this.timeframe = timeframe;
        this.units = units;
        this.timeList = timeList;
    }

    @Override
    public void calculate(Model model) {
        DateTime start = Clock.now().minus(1, timeframe.toChronoUnit());
        DateTime end = Clock.now();

        //Only calculate statistics for projects visible to the user
        List<TrackedItem> trackedItems = model.getFilteredTrackedItemList();

        timeList.clear();

        for (TrackedItem trackedItem : trackedItems) {
            long totalDuration = calculateTimeSpent(trackedItem, start, end);

            StatisticEntry entry = new StatisticEntry(trackedItem.getName().fullName, totalDuration);

            timeList.add(entry);
        }
    }

    private long calculateTimeSpent(TrackedItem trackedItem, DateTime start, DateTime end) {
        List<WorkDuration> durations = trackedItem.getDurationList();
        long totalDuration = 0;

        for (WorkDuration duration : durations) {
            DateTime startTime = duration.getStartTime();
            DateTime stopTime = duration.getStopTime();

            if (stopTime.isBefore(start) || startTime.isAfter(end)) {
                continue;
            }

            if (startTime.isBefore(start) && stopTime.isBefore(end)) {
                // Duration is cut in two by the timeframe
                totalDuration += DateTime.getTimeBetween(start, stopTime, units);
            } else {
                // Whole Duration is in the timeframe
                totalDuration += duration.getTimeBetween(units);
            }
        }

        return totalDuration;
    }

    @Override
    public ObservableList<StatisticEntry> getDisplayList() {
        return timeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PeriodicTotalTimeStatistic that = (PeriodicTotalTimeStatistic) o;
        return timeframe.equals(that.timeframe)
                && units == that.units
                && Objects.equals(timeList, that.timeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeframe, timeList);
    }
}
