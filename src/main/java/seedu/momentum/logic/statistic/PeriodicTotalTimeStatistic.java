//@@author boundtotheearth
package seedu.momentum.logic.statistic;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTimeWrapper;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;
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
     * Constructs a {@code PeriodicTotalTimeStatistic}.
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
     * Constructs a {@code PeriodicTotalTimeStatistic} with the specified data.
     *
     * @param timeframe Timeframe to track.
     * @param units Units for the total timeWrapper calculated.
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

    /**
     * Calculates the total time spent on each project in the provided model, for a specific timeframe.
     * The calculated data is saved, and can be retrieved using {@code getDisplayList}.
     *
     * @param model The data required to calculate the statistic.
     */
    @Override
    public void calculate(Model model) {
        DateTimeWrapper start = Clock.now().adjustToStartOfTimeframe(timeframe);
        DateTimeWrapper end = Clock.now();

        //Only calculate statistics for projects visible to the user
        List<TrackedItem> trackedItems = model.getDisplayList();

        timeList.clear();

        for (TrackedItem trackedItem : trackedItems) {
            long totalDuration = calculateTimeSpent(trackedItem, start, end);

            StatisticEntry entry = new StatisticEntry(trackedItem.getName().fullName, totalDuration);

            timeList.add(entry);
        }
    }

    /**
     * Calculates the time spent on a specific item, for the specified timeframe.
     *
     * @param trackedItem The item to calculate for.
     * @param start Start of the timeframe.
     * @param end End of the timeframe.
     * @return Total time spent for the item for the specified timeframe.
     */
    private long calculateTimeSpent(TrackedItem trackedItem, DateTimeWrapper start, DateTimeWrapper end) {
        List<WorkDuration> durations = trackedItem.getDurationList();
        long totalDuration = 0;

        for (WorkDuration duration : durations) {
            DateTimeWrapper startTime = duration.getStartTime();
            DateTimeWrapper stopTime = duration.getStopTime();

            if (stopTime.isBefore(start) || startTime.isAfter(end)) {
                continue;
            }

            if (startTime.isBefore(start) && stopTime.isBefore(end)) {
                // Duration is cut in two by the timeframe
                totalDuration += DateTimeWrapper.getTimeBetween(start, stopTime, units);
            } else {
                // Whole Duration is in the timeframe
                totalDuration += duration.getTimeBetween(units);
            }
        }

        if (!trackedItem.isTask()) {
            Project project = (Project) trackedItem;
            for (TrackedItem taskItem : project.getTaskList()) {
                totalDuration += calculateTimeSpent(taskItem, start, end);
            }
        }

        return totalDuration;
    }


    @Override
    public void setTimeframe(StatisticTimeframe timeframe) {
        this.timeframe = timeframe;
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
