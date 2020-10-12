package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.momentum.logic.statistic.StatisticEntry;

/**
 * An UI component that displays information of a {@code StatisticEntry}.
 */
public class StatCard extends UiPart<Region> {

    private static final String FXML = "StatCard.fxml";

    public final StatisticEntry statisticEntry;

    @FXML
    private Label label;

    @FXML
    private Label value;

    /**
     * Creates a {@code StatCard} with the given {@code statisticEntry} to display.
     */
    public StatCard(StatisticEntry statisticEntry) {
        super(FXML);
        this.statisticEntry = statisticEntry;
        label.setText(statisticEntry.getLabel());
        value.setText(formatToString(statisticEntry.getValue()));
    }

    private String formatToString(double value) {
        String output = "";
        int hours = (int) Math.floor(value / 60);
        int minutes = (int) value % 60;

        if (hours > 0) {
            output += String.format("%d hr ", hours);
        }

        if (minutes > 0) {
            output += String.format("%d min", minutes);
        }

        return output;
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
