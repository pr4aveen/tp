package seedu.momentum.logic.statistic;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Tracks the total timeWrapper spent on each project for a specific period of timeWrapper.
 */
public class PeriodicTotalTimeStatistic extends Statistic {

    private ChronoUnit period;
    private ChronoUnit units;

    private ObservableList<StatisticEntry> timeList = FXCollections.observableArrayList();

    /**
     * Constructs a {@code PeriodicTotalTimeStatistic}
     *
     * @param period Period of timeWrapper to track.
     * @param units Units for the total timeWrapper calculated.
     */
    public PeriodicTotalTimeStatistic(ChronoUnit period, ChronoUnit units) {
        requireAllNonNull(period, units);
        this.period = period;
        this.units = units;
    }

    /**
     * Constructs a {@code PeriodicTotalTimeStatistic} with specified data.
     *
     * @param period Period of timeWrapper to track.
     * @param units Units for the total timeWrapper calculated.
     * @param timeList Data for this statistic.
     */
    public PeriodicTotalTimeStatistic(ChronoUnit period,
                                      ChronoUnit units,
                                      ObservableList<StatisticEntry> timeList) {
        requireAllNonNull(period, units, timeList);
        this.period = period;
        this.units = units;
        this.timeList = timeList;
    }

    @Override
    public void calculate(Model model) {
        DateTimeWrapper weekStart = Clock.now().minus(1, period);
        DateTimeWrapper weekEnd = Clock.now();

        //Only calculate statistics for projects visible to the user
        List<TrackedItem> trackedItems = model.getFilteredTrackedItemList();

        timeList.clear();

        for (TrackedItem trackedItem : trackedItems) {
            long totalDuration = calculateTimeSpent(trackedItem, weekStart, weekEnd);

            StatisticEntry entry = new StatisticEntry(trackedItem.getName().fullName, totalDuration);

            timeList.add(entry);
        }
    }

    private long calculateTimeSpent(TrackedItem trackedItem, DateTimeWrapper weekStart, DateTimeWrapper weekEnd) {
        List<WorkDuration> durations = trackedItem.getDurationList();
        long totalDuration = 0;

        for (WorkDuration duration : durations) {
            DateTimeWrapper startTime = duration.getStartTime();
            DateTimeWrapper stopTime = duration.getStopTime();

            if (stopTime.isBefore(weekStart) || startTime.isAfter(weekEnd)) {
                continue;
            }

            if (startTime.isBefore(weekStart) && stopTime.isBefore(weekEnd)) {
                // Duration is cut in two by the week
                totalDuration += DateTimeWrapper.getTimeBetween(weekStart, stopTime, units);
            } else {
                // Whole Duration is in the week
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
        return period == that.period
                && units == that.units
                && Objects.equals(timeList, that.timeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, timeList);
    }
}
