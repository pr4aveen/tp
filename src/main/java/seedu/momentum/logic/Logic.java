package seedu.momentum.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.logic.commands.CommandResult;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.logic.statistic.StatisticGenerator;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.project.TrackedItem;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ProjectBook.
     *
     * @see seedu.momentum.model.Model#getProjectBook()
     */
    ReadOnlyProjectBook getProjectBook();

    /**
     * Returns an unmodifiable view of the filtered list of tracked items.
     */
    ObservableList<TrackedItem> getFilteredTrackedItemList();

    /**
     * Returns a list of projects whose timers are running.
     */
    ObservableList<TrackedItem> getRunningTimers();

    /**
     * Returns the user prefs' project book file path.
     */
    Path getProjectBookFilePath();

    /**
     * Returns the user prefs' GUI window settings.
     */
    GuiWindowSettings getGuiWindowSettings();

    /**
     * Set the user prefs' GUI window settings.
     */
    void setGuiWindowSettings(GuiWindowSettings guiWindowSettings);

    /**
     * Returns the user prefs' GUI theme settings.
     */
    GuiThemeSettings getGuiThemeSettings();

    /**
     * Set the user prefs' GUI theme settings.
     */
    void setGuiThemeSettings(GuiThemeSettings guiThemeSettings);

    /**
     * Return the user prefs' statistic timeframe settings.
     */
    StatisticTimeframeSettings getStatisticTimeframeSettings();

    /**
     * Set the user prefs' statistic timeframe settings.
     */
    void setStatisticTimeframeSettings(StatisticTimeframeSettings statisticTimeframeSettings);

    StatisticGenerator getStatistic();
}
