package seedu.momentum.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.logic.Logic;

public class TestStatWindow extends UiPart<Stage> {
    private static final String FXML = "TestStatWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(TestStatWindow.class);

    @FXML
    private PieChart piechart;

    /**
     * Creates a new TestStatWindow.
     *
     * @param root Stage to use as the root of the TestStatWindow.
     */
    public TestStatWindow(Stage root, Logic logic) {
        super(FXML, root);
        ObservableList<PieChart.Data> dataList =
                FXCollections.observableArrayList(logic
                        .getStatistic()
                        .getWeeklyTimePerProjectStatistic()
                        .stream()
                        .map(entry -> new PieChart.Data(entry.getLabel(), entry.getValue()))
                        .collect(Collectors.toList()));
        piechart.setData(dataList);
    }
    /**
     * Creates a new TestStatWindow.
     */
    public TestStatWindow(Logic logic) {
        this(new Stage(), logic);
    }

    /**
     * Shows the stat window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
