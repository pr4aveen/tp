package seedu.momentum.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.work_duration.DurationUtil;
import seedu.momentum.model.work_duration.WorkDuration;
import seedu.momentum.ui.UiPart;

/**
 * An UI component that displays information of a {@code WorkDuration}.
 */
public class DurationCard extends UiPart<Region> {

    private static final String FXML = "DurationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final WorkDuration duration;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label stopTime;

    /**
     * Creates a {@code ProjectCode} with the given {@code WorkDuration} to display.
     */
    public DurationCard(WorkDuration duration, int displayedIndex) {
        super(FXML);
        this.duration = duration;
        id.setText(displayedIndex + ". ");
        startTime.setText("Start: " + duration.getStartTime().format(DurationUtil.dateTimeFormatter));
        stopTime.setText("Stop: " + duration.getStopTime().format(DurationUtil.dateTimeFormatter));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DurationCard)) {
            return false;
        }

        // state check
        DurationCard card = (DurationCard) other;
        return duration.equals(card.duration);
    }
}
