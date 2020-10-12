package seedu.momentum.logic.statistic;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateTime;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Tracks the total time spent on each project for a specific period of time.
 */
public class PeriodicTotalTimeStatistic extends Statistic {

    private ChronoUnit period;
    private ChronoUnit units;

    private ObservableList<StatisticEntry> timeList = FXCollections.observableArrayList();

    /**
     * Constructs a {@code PeriodicTotalTimeStatistic}
     *
     * @param period Period of time to track.
     * @param units Units for the total time calculated.
     */
    public PeriodicTotalTimeStatistic(ChronoUnit period, ChronoUnit units) {
        requireAllNonNull(period, units);
        this.period = period;
        this.units = units;
    }

    /**
     * Constructs a {@code PeriodicTotalTimeStatistic} with specified data.
     *
     * @param period Period of time to track.
     * @param units Units for the total time calculated.
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
        DateTime weekStart = Clock.now().minus(1, period);
        DateTime weekEnd = Clock.now();

        //Only calculate statistics for projects visible to the user
        List<Project> projects = model.getFilteredProjectList();

        ObservableList<StatisticEntry> newTimeList = FXCollections.observableArrayList();

        for (Project project : projects) {
            long totalDuration = calculateTimeSpent(project, weekStart, weekEnd);

            StatisticEntry entry = new StatisticEntry(project.getName().fullName, totalDuration);

            newTimeList.add(entry);
            timeList = newTimeList;
        }
    }

    private long calculateTimeSpent(Project project, DateTime weekStart, DateTime weekEnd) {
        List<WorkDuration> durations = project.getDurationList();
        long totalDuration = 0;

        for (WorkDuration duration : durations) {
            DateTime startTime = duration.getStartTime();
            DateTime stopTime = duration.getStopTime();

            if (stopTime.isBefore(weekStart) || startTime.isAfter(weekEnd)) {
                continue;
            }

            if (startTime.isBefore(weekStart) && stopTime.isBefore(weekEnd)) {
                // Duration is cut in two by the week
                totalDuration += DateTime.getTimeBetween(weekStart, stopTime, units);
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
