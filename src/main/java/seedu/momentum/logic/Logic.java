package seedu.momentum.logic;

import java.nio.file.Path;
import java.util.Set;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.logic.commands.CommandResult;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.logic.statistic.StatisticGenerator;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.tag.Tag;

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
     * Returns true if there is no reminder to display, false otherwise.
     *
     * @return A boolean value that can be observed for changes.
     */
    BooleanProperty isReminderEmpty();

    /**
     * Returns the string representation of the reminder.
     *
     * @return A String representing the reminder, and can be observed for changes.
     */
    StringProperty getReminder();

    /**
     * Returns the ProjectBook.
     *
     * @see seedu.momentum.model.Model#getProjectBook()
     */
    ReadOnlyProjectBook getProjectBook();

    /**
     * Returns an unmodifiable view of the items to be displayed in the UI.
     */
    ObjectProperty<ObservableList<TrackedItem>> getObservableDisplayList();

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
     * Returns a set of all the tags in the items being displayed.
     */
    Set<Tag> getVisibleTags();

    /**
     * Returns an observable boolean value representing whether tags are visible.
     *
     * @return true if tags is visible, false otherwise.
     */
    BooleanProperty getIsTagsVisible();

    /**
     * Returns the user prefs' statistic timeframe settings.
     */
    StatisticTimeframeSettings getStatisticTimeframeSettings();

    /**
     * Returns the statistic manager
     */
    StatisticGenerator getStatistic();

    /**
     * Returns the total number of both visible and invisble items in the current project/task.
     */
    int getTotalNumberOfItems();

    /**
     * Returns the current view mode.
     */
    ViewMode getViewMode();

    /**
     * Returns the project that is currently displayed if the application is in the task view.
     */
    Project getCurrentProject();
}
