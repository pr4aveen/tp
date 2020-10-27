package seedu.momentum.model;

import java.nio.file.Path;
import java.util.function.Predicate;

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
    /** {@code Predicate} that always evaluate to true */
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
    void setProjectBook(ReadOnlyProjectBook projectBook);

    /** Returns the ProjectBook */
    ReadOnlyProjectBook getProjectBook();

    /**
     * Returns true if a tracked item with the same identity as {@code trackedItem} exists in the project book.
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

    /** Returns an unmodifiable view of the filtered project list */
    ObservableList<TrackedItem> getFilteredTrackedItemList();

    /** Returns a list of projects whose timers are running */
    ObservableList<TrackedItem> getRunningTimers();

    /**
     * Adds the given project to the running timers list.
     * {@code project} must have a running timer.
     *
     * @param trackedItem item to add a timer to.
     */
    void addRunningTimer(TrackedItem trackedItem);

    /**
     * Removes the given project from the running timers list.
     * {@code project} must have a running timer.
     *
     * @param trackedItem item to remove timer from.
     */
    void removeRunningTimer(TrackedItem trackedItem);

    /**
     * Updates the filter of the filtered project list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProjectList(Predicate<TrackedItem> predicate);

    /**
     * Orders the list of projects in a way given by the {@code sortType}.
     *
     * @throws NullPointerException if {@code sortType} is null.
     */
    void orderFilteredProjectList(SortType sortType, boolean isAscending);

    void viewProjects();

    void viewTasks(Project project);

    void viewAll();

    void resetView();

    /**
     * Returns the project that the user is currently viewing.
     *
     * @return current project that the user is viewing
     */
    Project getCurrentProject();

    ViewMode getViewMode();
}
