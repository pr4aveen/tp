package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.logic.statistic.StatisticEntry;

/**
 * An UI component that displays information of a {@code StatisticEntry}.
 */
public class StatCard extends UiPart<Region> {

    private static final String FXML = "StatCard.fxml";

    public final StatisticEntry statisticEntry;

    @FXML
    private Label statLabel;

    @FXML
    private Label statValue;

    /**
     * Creates a {@code StatCard} with the given {@code statisticEntry} to display.
     */
    public StatCard(StatisticEntry statisticEntry) {
        super(FXML);
        this.statisticEntry = statisticEntry;
        statLabel.setText(statisticEntry.getLabel());
        statValue.setText(StringUtil.formatMinutesToString(statisticEntry.getValue()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatCard)) {
            return false;
        }

        // state check
        StatCard card = (StatCard) other;
        return statisticEntry.equals(card.statisticEntry);
    }
}
