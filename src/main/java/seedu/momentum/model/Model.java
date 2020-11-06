package seedu.momentum.model;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.momentum.commons.core.GuiThemeSettings;
import seedu.momentum.commons.core.GuiWindowSettings;
import seedu.momentum.commons.core.StatisticTimeframeSettings;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.comparators.SortType;
import seedu.momentum.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true.
     * Used to show all items.
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
     */
    boolean hasTrackedItem(TrackedItem trackedItem);

    /**
     * Deletes the given tracked item.
     * The tracked item must exist in the project book.
     */
    void deleteTrackedItem(TrackedItem target);

    /**
     * Adds the given tracked item.
     * {@code trackedItem} must not already exist in the project book.
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
     * Returns an unmodifiable view of the list of items to display.
     */
    ObservableList<TrackedItem> getDisplayList();

    /**
     * Returns the list of items to display, that can be observed for changes.
     */
    ObjectProperty<ObservableList<TrackedItem>> getObservableDisplayList();

    /**
     * Returns a list of TrackedItem whose timers are running.
     */
    ObservableList<TrackedItem> getRunningTimers();

    /**
     * Returns a set of tags that are currently visible.
     */
    Set<Tag> getVisibleTags();

    /**
     * Hide tags if shown, show tags when hidden.
     */
    void showOrHideTags();

    /**
     * Returns is tags visible boolean property.
     *
     * @return true if tags is visible, false otherwise.
     */
    BooleanProperty getIsTagsVisible();

    /**
     * Reschedule all reminders.
     */
    void rescheduleReminders();

    /**
     * Reschedule all reminders in project book.
     */
    void rescheduleReminder();

    /**
     * Remove reminder from a project.
     *
     * @param project the project to remove the reminder from.
     */
    void removeReminder(Project project);

    /**
     * Remove reminder from a task.
     *
     * @param project the project with task to remove the reminder from.
     * @param task    the task to remove the reminder from.
     */
    void removeReminder(Project project, Task task);

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
    void removeReminderShown();

    /**
     * Update the list of running timers.
     */
    void updateRunningTimers();

    /**
     * Updates the filter of the display list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updatePredicate(Predicate<TrackedItem> predicate);

    /**
     * Orders the display list in a way given by the {@code sortType}.
     *
     * @throws NullPointerException if {@code sortType} is null.
     */
    void updateOrder(SortType sortType, boolean isAscending, boolean changeSortByCompletionStatus);

    /**
     * Orders the display list in a way given by the {@code sortType}.
     *
     * @throws NullPointerException if {@code sortType} is null.
     */
    void updateOrder(SortType sortType, boolean isAscending);

    /**
     * Updates the display list to show all projects.
     */
    void viewProjects();

    /**
     * Updates the display list to show all tasks belonging to the specified project.
     */
    void viewTasks(Project project);

    /**
     * Resets the display list to show all items.
     */
    void resetView();

    /**
     * Returns the project that the user is currently viewing.
     *
     * @return current project that the user is viewing.
     */
    Project getCurrentProject();

    /**
     * Returns the current view of the model.
     */
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
    void resetUi(ViewMode viewMode);

    /**
     * Redoes previously undone command to reset state to before undo command.
     */
    void redoCommand();

    /**
     * Returns the total number of both visible and invisble items in the current display list.
     */
    int getTotalNumberOfItems();
}
