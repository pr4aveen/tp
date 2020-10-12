package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code StatisticEntry}.
 */
public class StatCard extends UiPart<Region> {

    private static final String FXML = "StatCard.fxml";

    //public final StatisticEntry statisticEntry;

    // TEMPORARY VARIABLE FOR TESTING
    public final String[] statisticEntry;

    @FXML
    private Label label;

    @FXML
    private Label value;

    /**
     * Creates a {@code StatCard} with the given {@code statisticEntry} to display.
     */
    //public StatCard(StatisticEntry statisticEntry) {
    //    super(FXML);
    //    this.statisticEntry = statisticEntry;
    //    label.setText(statisticEntry.getLabel());
    //    value.setText(Double.toString(statisticEntry.getValue()));
    //}


    // TEMPORARY METHOD FOR TESTING
    public StatCard(String[] statisticEntry) {
        super(FXML);
        this.statisticEntry = statisticEntry;
        label.setText(statisticEntry[0]);
        value.setText(statisticEntry[1]);
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
