package seedu.momentum.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.GuiSettings;
import seedu.momentum.commons.core.LogsCenter;
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

        return commandResult;
    }

    public StatisticGenerator getStatistic() {
        return statistic;
    }

    @Override
    public ReadOnlyProjectBook getProjectBook() {
        return model.getProjectBook();
    }

    @Override
    public ObservableList<TrackedItem> getFilteredTrackedItemList() {
        return model.getFilteredTrackedItemList();
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
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
