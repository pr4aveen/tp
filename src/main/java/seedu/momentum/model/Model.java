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
    Predicate<TrackedItem> PREDICATE_SHOW_ALL_TRACKED_ITEMS = x -> true;

    //=========== UserPrefs ==================================================================================
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     *
     * @param userPrefs New user preferences to replace with.
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
     *
     * @param guiWindowSettings New window settings to set to.
     */
    void setGuiWindowSettings(GuiWindowSettings guiWindowSettings);

    //@@author khoodehui
    /**
     * Returns the user prefs' GUI theme settings.
     */
    GuiThemeSettings getGuiThemeSettings();

    /**
     * Sets the user prefs' GUI theme settings.
     *
     * @param guiThemeSettings New theme settings to set to.
     */
    void setGuiThemeSettings(GuiThemeSettings guiThemeSettings);

    /**
     * Return the user prefs' statistic timeframe settings.
     */
    StatisticTimeframeSettings getStatisticTimeframeSettings();

    /**
     * Set the user prefs' statistic timeframe settings.
     *
     * @param statisticTimeframeSettings New statistic timeframe to set to.
     */
    void setStatisticTimeframeSettings(StatisticTimeframeSettings statisticTimeframeSettings);
    //@@author

    /**
     * Returns the user prefs' project book file path.
     */
    Path getProjectBookFilePath();

    /**
     * Sets the user prefs' project book file path.
     *
     * @param projectBookFilePath New path to set to.
     */
    void setProjectBookFilePath(Path projectBookFilePath);

    //=========== ProjectBook ================================================================================
    //@@author kkangs0226
    /**
     * Replaces the versioned project book data with the new data in {@code versionedProjectBook}.
     *
     * @param versionedProjectBook Versioned project book containing the new data to set to.
     */
    void setVersionedProjectBook(ReadOnlyProjectBook versionedProjectBook);
    //@@author

    /**
     * Returns the ProjectBook.
     */
    ReadOnlyProjectBook getProjectBook();

    //@@author pr4aveen
    /**
     * Returns the project that the user is currently viewing.
     */
    Project getCurrentProject();

    /**
     * Checks if a tracked item exists inside the project book.
     *
     * @param trackedItem Tracked item to check against.
     * @return True if a tracked item with the same identity as {@code trackedItem} exists in the project book,
     * false otherwise.
     */
    boolean hasTrackedItem(TrackedItem trackedItem);

    /**
     * Deletes the given tracked item.
     * The tracked item must exist in the project book.
     *
     * @param target Tracked item to delete from the project book.
     */
    void deleteTrackedItem(TrackedItem target);

    /**
     * Adds the given tracked item.
     * {@code trackedItem} must not already exist in the project book.
     *
     * @param trackedItem Tracked item to add into the project book.
     */
    void addTrackedItem(TrackedItem trackedItem);

    /**
     * Replaces the given tracked item {@code target} with {@code editedTrackedItem}.
     * {@code target} must exist in the project book.
     * The tracked item identity of {@code editedTrackedItem} must not be the same as another existing tracked item in
     * the project book.
     *
     * @param target Old tracked item to be replaced.
     * @param editedTrackedItem New tracked item to replace the old item with.
     */
    void setTrackedItem(TrackedItem target, TrackedItem editedTrackedItem);

    //@@author boundtotheearth
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
     * Returns the current view of the model.
     */
    ViewMode getViewMode();

    //@@author pr4aveen
    /**
     * Returns the total number of both visible and invisible items in the current display list.
     */
    int getTotalNumberOfItems();

    //=========== Tags =======================================================================================
    /**
     * Returns a set of all the tags that are associated with the projects/tasks on the currently visible display list.
     */
    Set<Tag> getVisibleTags();

    //@@author claracheong4
    /**
     * Hide the tags window in the Ui if shown, shows the tags window when hidden.
     */
    void showOrHideTags();

    /**
     * Returns is tags visible boolean property.
     *
     * @return True if tags is visible, false otherwise.
     */
    BooleanProperty getIsTagsVisible();

    //=========== Filtered Project List Accessors ============================================================
    //@@author boundtotheearth
    /**
     * Returns an unmodifiable view of the list of items to display.
     */
    ObservableList<TrackedItem> getDisplayList();

    /**
     * Returns the list of items to display, that can be observed for changes.
     */
    ObjectProperty<ObservableList<TrackedItem>> getObservableDisplayList();

    //@@author pr4aveen
    /**
     * Updates the filter of the display list to filter by the given {@code predicate}.
     *
     * @param predicate Predicate to be used on the filtered list.
     * @throws NullPointerException If {@code predicate} is null.
     */
    void updatePredicate(Predicate<TrackedItem> predicate);

    //=========== Reminders ==================================================================================
    //@@author claracheong4
    /**
     * Reschedule all reminders in project book.
     */
    void rescheduleReminder();

    /**
     * Checks whether the reminder window has an active reminder.
     *
     * @return True if the reminder is empty, false otherwise.
     */
    BooleanProperty isReminderEmpty();

    /**
     * Returns the string representation of a reminder.
     *
     * @return The string presentation of the target reminder.
     */
    StringProperty getReminder();

    /**
     * Remove the reminder shown.
     */
    void removeReminderShown();

    /**
     * Remove reminder from a project.
     *
     * @param project The project to remove the reminder from.
     */
    void removeReminder(Project project);

    /**
     * Remove reminder from a task.
     *
     * @param project The project with task to remove the reminder from.
     * @param task    The task to remove the reminder from.
     */
    void removeReminder(Project project, Task task);

    //=========== Timers =====================================================================================
    //@@author boundtotheearth
    /**
     * Returns a list of TrackedItem whose timers are running.
     */
    ObservableList<TrackedItem> getRunningTimers();

    /**
     * Update the list of running timers.
     */
    void updateRunningTimers();

    //=========== Undo/Redo ==================================================================================
    //@@author kkangs0226
    /**
     * Returns true if model is able to undo command, false otherwise.
     */
    boolean canUndoCommand();

    /**
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
     * Redoes previously undone command to reset state to before undo command.
     */
    void redoCommand();

    /**
     * Undoes command to reset view mode to previous view mode.
     */
    void resetUi(ViewMode viewMode);

    //=========== Sorting ====================================================================================
    /**
     * Orders the display list in a way given by the {@code sortType}.
     *
     * @param sortType The type of sorting to be used on the displayed list.
     * @param isAscending Whether the list should be sorted in ascending order based on the given type.
     * @param changeSortByCompletionStatus Whether sort by completion status should be toggled.
     * @throws NullPointerException If {@code sortType} is null.
     */
    void updateOrder(SortType sortType, boolean isAscending, boolean changeSortByCompletionStatus);

    /**
     * Orders the display list in a way given by the {@code sortType}.
     *
     * @param sortType The type of sorting to be used on the displayed list.
     * @param isAscending Whether the list should be sorted in ascending order based on the given type.
     * @throws NullPointerException If {@code sortType} is null.
     */
    void updateOrder(SortType sortType, boolean isAscending);
}
