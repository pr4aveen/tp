package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.momentum.model.project.TrackedItem;

/**
 * An UI component that displays information of a {@code TimerWrapper}.
 */
public class TimerCard extends UiPart<Region> {

    private static final String FXML = "TimerCard.fxml";

    public final TrackedItem trackedItemEntry;

    @FXML
    private Label trackedItem;

    @FXML
    private Label startTime;

    /**
     * Creates a {@code TimerCard} with the given {@code projectEntry} to display.
     */
    public TimerCard(TrackedItem trackedItemEntry) {
        super(FXML);
        this.trackedItemEntry = trackedItemEntry;
        trackedItem.setText(trackedItemEntry.getName().fullName);
        startTime.setText(trackedItemEntry.getTimer().getStartTime().getFormatted());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimerCard)) {
            return false;
        }

        // state check
        TimerCard card = (TimerCard) other;
        return trackedItemEntry.equals(card.trackedItemEntry);
    }
}
