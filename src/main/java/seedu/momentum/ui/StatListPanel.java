package seedu.momentum.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing a list of time spent.
 */
public class StatListPanel extends UiPart<Region> {
    private static final String FXML = "StatListPanel.fxml";

    //@FXML
    //private ListView<StatisticEntry> statListView;

    // TEMPORARY VARIABLE FOR TESTING
    @FXML
    private ListView<String[]> statListView;

    // TEMPORARY CONSTRUCTOR FOR TESTING
    public StatListPanel() {
        super(FXML);
    }

    ///**
    // * Creates a {@code StatListPanel} with the given {@code ObservableList}.
    // */
    //public StatListPanel(ObservableList<StatisticEntry> statisticList) {
    //    super(FXML);
    //    statListView.setItems(statisticList);
    //    statListView.setCellFactory(listView -> new StatListViewCell());
    //}

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code StatisticEntry} using a {@code StatisticCard}.
     */
    //class StatListViewCell extends ListCell<StatisticEntry> {
    //    @Override
    //    protected void updateItem(StatisticEntry statisticEntry, boolean empty) {
    //        super.updateItem(statisticEntry, empty);

    //        if (empty || statisticEntry == null) {
    //            setGraphic(null);
    //            setText(null);
    //        } else {
    //            setGraphic(new StatCard(statisticEntry).getRoot());
    //        }
    //    }
    //}

    // TEMPORARY METHOD FOR TESTING
    public StatListPanel(ObservableList<String[]> statisticList) {
        super(FXML);
        statListView.setItems(statisticList);
        statListView.setCellFactory(listView -> new StatListViewCell());
    }

    // TEMPORARY CLASS FOR TESTING
    class StatListViewCell extends ListCell<String[]> {
        @Override
        protected void updateItem(String[] statisticEntry, boolean empty) {
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
