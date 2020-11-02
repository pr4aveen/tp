package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class BottomBar extends UiPart<Region> {

    private static final String FXML = "BottomBar.fxml";

    @FXML
    private Label viewDisplay;

    @FXML
    private Label timeDisplay;

    @FXML
    private Label countDisplay;


    /**
     * Creates a {@code BottomBar} with the given model.
     */
    public BottomBar(String view, String count) {
        super(FXML);
        viewDisplay.setText("Viewing all projects");
        timeDisplay.setText("Mon, 2 Nov 2020");
        countDisplay.setText("Showing 5/12 projects");
    }

}
