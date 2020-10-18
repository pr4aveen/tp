package seedu.momentum.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.momentum.logic.statistic.StatisticEntry;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.timer.Timer;

/**
 * Panel containing a list of time spent.
 */
public class TimerListPanel extends UiPart<Region> {
    private static final String FXML = "TimerListPanel.fxml";

    @FXML
    private ListView<Project> timerListView;

    /**
     * Creates a {@code StatListPanel} with the given {@code ObservableList}.
     */
    public TimerListPanel(ObservableList<Project> projectList) {
        super(FXML);
        timerListView.setItems(projectList);
        timerListView.setCellFactory(listView -> new TimerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Timer} using a {@code TimerCard}.
     */
    class TimerListViewCell extends ListCell<Project> {
        @Override
        protected void updateItem(Project projectEntry, boolean empty) {
            super.updateItem(projectEntry, empty);

            if (empty || projectEntry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TimerCard(projectEntry).getRoot());
            }
        }
    }
}
