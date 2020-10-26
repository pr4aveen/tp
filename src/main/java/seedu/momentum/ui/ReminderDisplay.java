package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

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
     * @param isEmpty true if the reminder is empty.
     * @param reminderStr string representation of the reminder.
     */
    public ReminderDisplay(boolean isEmpty, String reminderStr) {
        super(FXML);
        this.isEmpty = isEmpty;
        this.reminderStr = reminderStr;
        fillReminder();
    }

    private void fillReminder() {
        if (!isEmpty) {
            reminderPane.getChildren().add(new Label(reminderStr));
        }
    }
}
