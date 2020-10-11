package seedu.momentum.logic.statistic;

import javafx.collections.ObservableList;
import seedu.momentum.model.Model;

/**
 * Represents a statistic tracked by the app and the instructions to calculate it.
 */
public abstract class Statistic {

    /**
     * Calculates the statistic being tracked.
     *
     * @param model The data required to calculate the statistic.
     */
    public abstract void calculate(Model model);
    public abstract ObservableList<StatisticEntry> getDisplayList();
}
