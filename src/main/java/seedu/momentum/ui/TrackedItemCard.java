package seedu.momentum.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.momentum.model.project.TrackedItem;

/**
 * An UI component that displays information of a {@code Project}.
 */
public class TrackedItemCard extends UiPart<Region> {

    private static final String FXML = "TrackedItemListCard.fxml";

    private static final String STYLE_TEXT = "-fx-text-fill: ";
    private static final String STYLE_COLOUR_RED = "-fx-red";
    private static final String STYLE_COLOUR_GREEN = "-fx-green";
    private static final String STYLE_COLOUR_YELLOW = "-fx-yellow";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final TrackedItem trackedItem;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label completionStatus;
    @FXML
    private Label reminderStatus;
    @FXML
    private Text id;
    @FXML
    private HBox description;
    @FXML
    private HBox createdDate;
    @FXML
    private HBox deadline;
    @FXML
    private HBox reminder;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ProjectCard} with the given {@code Project} and index to display.
     */
    public TrackedItemCard(TrackedItem trackedItem, int displayedIndex) {
        super(FXML);
        this.trackedItem = trackedItem;
        id.setText(displayedIndex + ". ");

        name.setText(trackedItem.getName().fullName);

        setDescriptionLabel(trackedItem);

        completionStatus.setText(trackedItem.getCompletionStatus().toString());
        setCompletionStatusStyle(completionStatus);

        reminderStatus.setText(trackedItem.getReminder().getStatus());
        setReminderStatusStyle(reminderStatus);

        createdDate.getChildren().add(new Label("Created: " + trackedItem.getCreatedDate().getFormatted()));

        Label deadlineLabel = new Label("Due: " + trackedItem.getDeadline().getFormattedDeadline());
        setDeadlineStyle(deadlineLabel);
        deadline.getChildren().add(deadlineLabel);

        Label reminderLabel = new Label("Reminder: " + trackedItem.getReminder().getFormattedReminder());
        reminder.getChildren().add(reminderLabel);

        setTagsPane(trackedItem);
    }

    private void setDescriptionLabel(TrackedItem trackedItem) {
        if (!trackedItem.getDescription().isEmpty()) {
            Label descLabel = new Label(trackedItem.getDescription().value);
            descLabel.setWrapText(true);
            description.getChildren().add(descLabel);
        }
    }

    private void setTagsPane(TrackedItem trackedItem) {
        trackedItem.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void setCompletionStatusStyle(Label completionStatus) {
        String style = STYLE_TEXT;

        if (trackedItem.getCompletionStatus().isCompleted()) {
            style += STYLE_COLOUR_GREEN;
        } else {
            style += STYLE_COLOUR_RED;
        }

        completionStatus.setStyle(style);
    }

    private void setReminderStatusStyle(Label reminderStatus) {
        reminderStatus.setStyle(STYLE_TEXT + STYLE_COLOUR_YELLOW);
    }

    private void setDeadlineStyle(Label deadline) {
        String style = STYLE_TEXT;

        if (trackedItem.getDeadline().isEmpty()) {
            style += "-fx-cool-gray-0";
        } else {
            long daysToDeadline = trackedItem.getDeadline().daysToDeadline();
            if (daysToDeadline > 7) {
                style += STYLE_COLOUR_GREEN;
            } else if (daysToDeadline < 4) {
                style += STYLE_COLOUR_RED;
            } else {
                style += STYLE_COLOUR_YELLOW;
            }
        }

        deadline.setStyle(style);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TrackedItemCard)) {
            return false;
        }

        // state check
        TrackedItemCard card = (TrackedItemCard) other;
        return id.getText().equals(card.id.getText())
                && trackedItem.equals(card.trackedItem);
    }
}
