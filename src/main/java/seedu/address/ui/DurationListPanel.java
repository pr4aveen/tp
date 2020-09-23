package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.project.Project;
import seedu.address.model.work_duration.WorkDuration;

/**
 * Panel containing the list of projects.
 */
public class DurationListPanel extends UiPart<Region> {
    private static final String FXML = "DurationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DurationListPanel.class);

    @FXML
    private ListView<WorkDuration> durationListView;

    /**
     * Creates a {@code DurationListPanel} with the given {@code ObservableList}.
     */
    public DurationListPanel(ObservableList<WorkDuration> durationList) {
        super(FXML);
        durationListView.setItems(durationList);
        durationListView.setCellFactory(listView -> new DurationListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code WorkDuration} using a {@code DurationCard}.
     */
    class DurationListViewCell extends ListCell<WorkDuration> {
        @Override
        protected void updateItem(WorkDuration duration, boolean empty) {
            super.updateItem(duration, empty);

            if (empty || duration == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DurationCard(duration, getIndex() + 1).getRoot());
            }
        }
    }

}
