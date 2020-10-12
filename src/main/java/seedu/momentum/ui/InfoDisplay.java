package seedu.momentum.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A UI component that displays certain information to the user.
 */
public class InfoDisplay extends UiPart<Region> {

    private static final String FXML = "InfoDisplay.fxml";

    @FXML
    private Label infoName;

    /**
     * Creates a {@code InfoDisplay} with the given {@code infoName}.
     */
    public InfoDisplay(String infoName) {
        super(FXML);
        this.infoName.setText(infoName);
    }
}
