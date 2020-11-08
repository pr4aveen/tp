//@@author boundtotheearth

package seedu.momentum.logic.statistic;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.model.Model;

/**
 * Represents a statistic tracked by Momentum and the instructions to calculate it.
 */
public abstract class Statistic {

    /**
     * Calculates the statistic being tracked.
     *
     * @param model The data required to calculate the statistic.
     */
    public abstract void calculate(Model model);

    //@@author khoodehui
    /**
     * Sets the timeframe of the statistic tracked.
     *
     * @param statisticTimeframe Timeframe to set the statistics of the item to.
     */
    public abstract void setTimeframe(StatisticTimeframe statisticTimeframe);
    //@@author

    /**
     * Retrieves the statistics data in a displayable format.
     *
     * @return The displayable list of the statistics data.
     */
    public abstract ObservableList<StatisticEntry> getDisplayList();
}
