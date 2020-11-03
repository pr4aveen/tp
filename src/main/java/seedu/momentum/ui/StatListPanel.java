package seedu.momentum.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.momentum.commons.core.StatisticTimeframe;
import seedu.momentum.logic.statistic.StatisticEntry;

/**
 * Panel containing a list of time spent.
 */
public class StatListPanel extends UiPart<Region> {
    private static final String FXML = "StatListPanel.fxml";

    private PieChartCard pieChartCard;

    @FXML
    private ListView<StatisticEntry> statListView;

    @FXML
    private StackPane pieChartPlaceHolder;

    @FXML
    private Label title;

    /**
     * Creates a {@code StatListPanel} with the given {@code ObservableList}.
     */
    public StatListPanel(ObservableList<StatisticEntry> statisticList, StatisticTimeframe timeframe) {
        super(FXML);
        title.setText(timeframe + " Time Spent");
        statListView.setItems(statisticList);
        statListView.setCellFactory(listView -> new StatListViewCell());
        initPieChart(statisticList);
    }

    private void initPieChart(ObservableList<StatisticEntry> statisticList) {
        statisticList.addListener((ListChangeListener<? super StatisticEntry>) c -> updatePieChart(statisticList));
        pieChartCard = new PieChartCard(statisticList);
        pieChartPlaceHolder.getChildren().add(pieChartCard.getRoot());
    }

    private void updatePieChart(ObservableList<StatisticEntry> statisticList) {
        PieChartCard newPieChartCard = new PieChartCard(statisticList);
        pieChartPlaceHolder.getChildren().clear();
        pieChartPlaceHolder.getChildren().add(newPieChartCard.getRoot());
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
