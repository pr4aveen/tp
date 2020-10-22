package seedu.momentum.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

/**
 * Panel containing the list of projects.
 */
public class TrackedItemListPanel extends UiPart<Region> {
    private static final String FXML = "TrackedItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TrackedItemListPanel.class);

    @FXML
    private ListView<TrackedItem> trackedItemListView;

    /**
     * Creates a {@code ProjectListPanel} with the given {@code ObservableList}.
     */
    public TrackedItemListPanel(ObservableList<TrackedItem> trackedItemList) {
        super(FXML);
        trackedItemListView.setItems(trackedItemList);
        trackedItemListView.setCellFactory(listView -> new TrackedItemViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Project} using a {@code ProjectCard}.
     */
    class TrackedItemViewCell extends ListCell<TrackedItem> {
        @Override
        protected void updateItem(TrackedItem trackedItem, boolean empty) {
            super.updateItem(trackedItem, empty);

            if (empty || trackedItem == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TrackedItemCard(trackedItem, getIndex() + 1).getRoot());
            }
        }
    }
}
