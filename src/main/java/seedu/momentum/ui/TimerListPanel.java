package seedu.momentum.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

/**
 * Panel containing a list of running timers.
 */
public class TimerListPanel extends UiPart<Region> {
    private static final String FXML = "TimerListPanel.fxml";

    @FXML
    private ListView<TrackedItem> timerListView;

    /**
     * Creates a {@code TimerListPanel} with the given {@code ObservableList}.
     */
    public TimerListPanel(ObservableList<TrackedItem> projectList) {
        super(FXML);
        timerListView.setItems(projectList);
        timerListView.setCellFactory(listView -> new TimerListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Timer} using a {@code TimerCard}.
     */
    class TimerListViewCell extends ListCell<TrackedItem> {
        @Override
        protected void updateItem(TrackedItem trackedItem, boolean empty) {
            super.updateItem(trackedItem, empty);

            if (empty || trackedItem == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TimerCard(trackedItem).getRoot());
            }
        }
    }
}
