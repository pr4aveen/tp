package seedu.momentum.ui;

import javafx.scene.layout.Region;
import seedu.momentum.logic.statistic.StatisticEntry;

/**
 * A UI component that displays pie chart of statistics shown in the statistics panel.
 */
public class PieChartCard extends UiPart<Region> {

    private static final String FXML = "PieChartCard.fxml";

    public PieChartCard(StatisticEntry statisticEntry) {
        super(FXML);
    }
}
