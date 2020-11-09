//@@author claracheong4
package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

/**
 * A UI component to display the current reminder that exist inside the project book.
 */
public class ReminderDisplay extends UiPart<Region> {
    private static final String FXML = "ReminderDisplay.fxml";

    private boolean isEmpty;
    private String reminderStr;

    @FXML
    private FlowPane reminderPane;

    /**
     * Creates a {@code ReminderDisplay}.
     *
     * @param isEmpty     True if the reminder is empty.
     * @param reminderStr String representation of the reminder.
     */
    public ReminderDisplay(boolean isEmpty, String reminderStr) {
        super(FXML);
        this.isEmpty = isEmpty;
        this.reminderStr = reminderStr;
        fillReminder();
    }

    private void fillReminder() {
        if (!isEmpty) {
            Label reminderLabel = new Label(reminderStr);
            reminderLabel.setAlignment(Pos.CENTER_LEFT);
            reminderLabel.setTextAlignment(TextAlignment.LEFT);
            reminderLabel.setMaxWidth(reminderPane.getPrefWrapLength() * 8 / 10);
            reminderLabel.setWrapText(true);
            reminderPane.getChildren().add(reminderLabel);
        }
    }
}
