package seedu.momentum.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.LogsCenter;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.logic.commands.Command;
import seedu.momentum.logic.commands.CommandResult;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.ProjectBookParser;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.logic.statistic.StatisticGenerator;
import seedu.momentum.logic.statistic.StatisticManager;
import seedu.momentum.model.Model;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final StatisticGenerator statistic;
    private final ProjectBookParser projectBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        statistic = new StatisticManager(model);
        projectBookParser = new ProjectBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        model.resetView();

        CommandResult commandResult;
        Command command = projectBookParser.parseCommand(commandText, model);
        commandResult = command.execute(model);

        try {
            storage.saveProjectBook(model.getProjectBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        statistic.updateStatistics();
        model.updateRunningTimers();

        return commandResult;
    }

    public StatisticGenerator getStatistic() {
        return statistic;
    }

    @Override
    public BooleanProperty isReminderEmpty() {
        return model.isReminderEmpty();
    }

    @Override
    public StringProperty getReminder() {
        return model.getReminder();
    }

    @Override
    public ReadOnlyProjectBook getProjectBook() {
        return model.getProjectBook();
    }

    @Override
    public ObjectProperty<ObservableList<TrackedItem>> getObservableDisplayList() {
        return model.getObservableDisplayList();
    }

    @Override
    public ObservableList<TrackedItem> getRunningTimers() {
        return model.getRunningTimers();
    }

    @Override
    public Path getProjectBookFilePath() {
        return model.getProjectBookFilePath();
    }

    @Override
    public GuiWindowSettings getGuiWindowSettings() {
        return model.getGuiWindowSettings();
    }

    @Override
    public void setGuiWindowSettings(GuiWindowSettings guiWindowSettings) {
        model.setGuiWindowSettings(guiWindowSettings);
    }

    @Override
    public GuiThemeSettings getGuiThemeSettings() {
        return model.getGuiThemeSettings();
    }

    @Override
    public void setGuiThemeSettings(GuiThemeSettings guiThemeSettings) {
        model.setGuiThemeSettings(guiThemeSettings);
    }

    @Override
    public StatisticTimeframeSettings getStatisticTimeframeSettings() {
        return model.getStatisticTimeframeSettings();
    }

    @Override
    public void setStatisticTimeframeSettings(StatisticTimeframeSettings statisticTimeframe) {
        model.setStatisticTimeframeSettings(statisticTimeframe);
    }
}
