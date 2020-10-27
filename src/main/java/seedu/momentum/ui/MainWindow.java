package seedu.momentum.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.logic.Logic;
import seedu.momentum.logic.commands.CommandResult;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.logic.statistic.StatisticEntry;
import seedu.momentum.model.project.TrackedItem;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private CommandBox commandBox;
    private ResultDisplay resultDisplay;
    private TrackedItemListPanel trackedItemListPanel;
    private TagsDisplay tagsDisplay;
    private TimerListPanel timerListPanel;
    private StatListPanel statListPanel;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem testStatMenuItem;

    @FXML
    private StackPane projectListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statListPanelPlaceholder;

    @FXML
    private StackPane timerListPanelPlaceholder;

    @FXML
    private StackPane infoDisplayPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiWindowSettings());
        setDefaultTheme(logic.getGuiThemeSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        initCommandBox();
        initResultDisplay();
        initProjectList();
        initTagDisplay();
        initTimerList();
        initStatList();
    }

    private void initCommandBox() {
        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    private void initResultDisplay() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
    }

    private void initProjectList() {
        trackedItemListPanel = new TrackedItemListPanel(logic.getFilteredTrackedItemList());
        projectListPanelPlaceholder.getChildren().add(trackedItemListPanel.getRoot());
    }

    private void initTagDisplay() {
        tagsDisplay = new TagsDisplay(logic.getProjectBook().getTrackedItemTags());
        infoDisplayPlaceholder.getChildren().add(tagsDisplay.getRoot());

        // Add a listener to the project list that will update tags when there are changes made to the project list.
        logic.getFilteredTrackedItemList().addListener((ListChangeListener<TrackedItem>) c -> {
            TagsDisplay newTagsDisplay = new TagsDisplay(logic.getProjectBook().getTrackedItemTags());
            infoDisplayPlaceholder.getChildren().clear();
            infoDisplayPlaceholder.getChildren().add(newTagsDisplay.getRoot());
        });
    }

    private void initStatList() {
        ObservableList<StatisticEntry> stats = logic.getStatistic().getWeeklyTimePerProjectStatistic();
        statListPanel = new StatListPanel(stats);
        statListPanelPlaceholder.getChildren().add(statListPanel.getRoot());
    }

    private void initTimerList() {
        timerListPanel = new TimerListPanel(logic.getRunningTimers());
        timerListPanelPlaceholder.getChildren().add(timerListPanel.getRoot());
    }

    /**
     * Sets the default size based on {@code guiWindowSettings}.
     */
    private void setWindowDefaultSize(GuiWindowSettings guiWindowSettings) {
        primaryStage.setHeight(guiWindowSettings.getWindowHeight());
        primaryStage.setWidth(guiWindowSettings.getWindowWidth());
        if (guiWindowSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiWindowSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiWindowSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Sets the default theme based on {@code guiThemeSetting}.
     */
    private void setDefaultTheme(GuiThemeSettings guiThemeSettings) {
        primaryStage.getScene().getStylesheets().add(guiThemeSettings.getTheme().getStylesheet());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiWindowSettings guiWindowSettings = new GuiWindowSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiWindowSettings(guiWindowSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public TrackedItemListPanel getTrackedItemListPanel() {
        return trackedItemListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.momentum.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
