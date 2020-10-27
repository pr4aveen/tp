package seedu.momentum.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.logic.statistic.StatisticEntry;

/**
 * Panel containing a list of time spent.
 */
public class StatListPanel extends UiPart<Region> {
    private static final String FXML = "StatListPanel.fxml";
    private StatisticTimeframe timeframe;

    @FXML
    private ListView<StatisticEntry> statListView;

    @FXML
    private Label title;

    /**
     * Creates a {@code StatListPanel} with the given {@code ObservableList}.
     */
    public StatListPanel(ObservableList<StatisticEntry> statisticList, StatisticTimeframe timeframe) {
        super(FXML);
        this.timeframe = timeframe;
        setTitle();
        statListView.setItems(statisticList);
        statListView.setCellFactory(listView -> new StatListViewCell());
    }

    private void setTitle() {
        String title = " Time Spent";

        switch (timeframe.getTimeframe()) {
        case DAYS:
            title = "Daily" + title;
            break;
        case WEEKS:
            title = "Weekly" + title;
            break;
        case MONTHS:
            title = "Monthly" + title;
            break;
        default:
            // Should not reach default block.
            break;
        }

        this.title.setText(title);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code StatisticEntry} using a {@code StatisticCard}.
     */
    class StatListViewCell extends ListCell<StatisticEntry> {
        @Override
        protected void updateItem(StatisticEntry statisticEntry, boolean empty) {
            super.updateItem(statisticEntry, empty);

            if (empty || statisticEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StatCard(statisticEntry).getRoot());
            }
        }
    }
}
