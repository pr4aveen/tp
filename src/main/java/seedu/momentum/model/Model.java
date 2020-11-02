package seedu.momentum.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.project.TrackedItem;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true.
     */
    Predicate<TrackedItem> PREDICATE_SHOW_ALL_TRACKED_ITEMS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI window settings.
     */
    GuiWindowSettings getGuiWindowSettings();

    /**
     * Sets the user prefs' GUI window settings.
     */
    void setGuiWindowSettings(GuiWindowSettings guiWindowSettings);

    /**
     * Returns the user prefs' GUI theme settings.
     */
    GuiThemeSettings getGuiThemeSettings();

    /**
     * Sets the user prefs' GUI theme settings.
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

    /**
     * Returns the user prefs' project book file path.
     */
    Path getProjectBookFilePath();

    /**
     * Sets the user prefs' project book file path.
     */
    void setProjectBookFilePath(Path projectBookFilePath);

    /**
     * Replaces project book data with the data in {@code projectBook}.
     */
    void setVersionedProjectBook(ReadOnlyProjectBook versionedProjectBook);

    /**
     * Returns the ProjectBook.
     */
    ReadOnlyProjectBook getProjectBook();

    /**
     * Returns true if a tracked item with the same identity as {@code trackedItem} exists in the project book.
     *
     * @param trackedItem
     */
    boolean hasTrackedItem(TrackedItem trackedItem);

    /**
     * Deletes the given project.
     * The project must exist in the project book.
     */
    void deleteTrackedItem(TrackedItem target);

    /**
     * Adds the given project.
     * {@code project} must not already exist in the project book.
     */
    void addTrackedItem(TrackedItem trackedItem);

    /**
     * Replaces the given tracked item {@code target} with {@code editedTrackedItem}.
     * {@code target} must exist in the project book.
     * The tracked item identity of {@code editedTrackedItem} must not be the same as another existing tracked item in
     * the project book.
     */
    void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem);

    /**
     * Returns an unmodifiable view of the filtered project list.
     */
    ObservableList<TrackedItem> getDisplayList();

    ObjectProperty<ObservableList<TrackedItem>> getObservableDisplayList();

    /**
     * Returns a list of projects whose timers are running.
     */
    ObservableList<TrackedItem> getRunningTimers();

    /**
     * Reschedule all reminders.
     */
    void rescheduleReminders();

    /**
     * Returns true if the reminder is empty, false otherwise.
     *
     * @return the boolean.
     */
    BooleanProperty isReminderEmpty();

    /**
     * Returns the string representation of the reminder.
     *
     * @return the reminder.
     */
    StringProperty getReminder();

    /**
     * Remove the reminder shown.
     */
    void removeReminder();

    void updateRunningTimers();

    /**
     * Updates the filter of the filtered project list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updatePredicate(Predicate<TrackedItem> predicate);

    /**
     * Returns the current {@code predicate} applied to the filtered list.
     */
    Predicate<TrackedItem> getCurrentPredicate();

    /**
     * Orders the list of projects in a way given by the {@code sortType}.
     *
     * @throws NullPointerException if {@code sortType} is null.
     */
    void updateOrder(SortType sortType, boolean isAscending, boolean isSortedByCompletionStatus);

    void viewProjects();

    void viewTasks(Project project);

    void viewAll();

    void resetView();

    /**
     * Returns the project that the user is currently viewing.
     *
     * @return current project that the user is viewing.
     */
    Project getCurrentProject();

    ViewMode getViewMode();


    //=========== Undo & Redo ================================================================================

    /**
     * Returns true if model is able to undo command, false otherwise.
     */
    boolean canUndoCommand();

    /**
     * Returns true if model is able to redo undone command, false otherwise.
     */
    boolean canRedoCommand();

    /**
     * Commits current {@code ProjectBook} state to history.
     */
    void commitToHistory();

    /**
     * Undoes command to reset state to previous state in history.
     */
    void undoCommand();

    /**
     * Undoes command to reset view mode to previous view mode.
     */
    void resetUi(ViewMode viewMode, Project project);

    /**
     * Redoes previously undone command to reset state to before undo command.
     */
    void redoCommand();
}
