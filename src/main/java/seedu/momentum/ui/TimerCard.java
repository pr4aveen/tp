package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.momentum.logic.statistic.StatisticEntry;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.timer.Timer;

/**
 * An UI component that displays information of a {@code Timer}.
 */
public class TimerCard extends UiPart<Region> {

    private static final String FXML = "TimerCard.fxml";

    public final Project projectEntry;

    @FXML
    private Label project;

    @FXML
    private Label startTime;

    /**
     * Creates a {@code StatCard} with the given {@code statisticEntry} to display.
     */
    public TimerCard(Project projectEntry) {
        super(FXML);
        this.projectEntry = projectEntry;
        project.setText(projectEntry.getName().fullName);
        startTime.setText(projectEntry.getTimer().getStartTime().getFormatted());
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
        return projectEntry.equals(card.projectEntry);
    }
}
