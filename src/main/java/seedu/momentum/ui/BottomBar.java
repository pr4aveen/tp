package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class BottomBar extends UiPart<Region> {

    private static final String FXML = "BottomBar.fxml";

    private static final String VIEWING_PROJECTS = "Viewing all projects.";
    private static final String VIEWING_TASKS = "Viewing tasks in %s.";
    private static final String VISIBLE_RESULTS = "Showing %d of %d results.";
    private static final String MOMENTUM = "Momentum";

    @FXML
    private Label viewDisplay;

    @FXML
    private Label momentumDisplay;

    @FXML
    private Label countDisplay;

    /**
     * Creates a bottom bar for the project view.
     *
     * @param totalVisible number of visible projects in the project book.
     * @param totalItems total number of projects in the project book.
     */
    public BottomBar(int totalVisible, int totalItems) {
        super(FXML);
        viewDisplay.setText(VIEWING_PROJECTS);
        momentumDisplay.setText(MOMENTUM);
        countDisplay.setText(String.format(VISIBLE_RESULTS, totalVisible, totalItems));
    }

    /**
     * Creates a bottom bar for the task view.
     *
     * @param totalVisible number of visible tasks in the specified project.
     * @param totalItems total number of tasks in the project book.
     * @param projectName currently viewed project.
     */
    public BottomBar(int totalVisible, int totalItems, String projectName) {
        super(FXML);
        viewDisplay.setText(String.format(VIEWING_TASKS, projectName));
        momentumDisplay.setText(MOMENTUM);
        countDisplay.setText(String.format(VISIBLE_RESULTS, totalVisible, totalItems));
    }

}
