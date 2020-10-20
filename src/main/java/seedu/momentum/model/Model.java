package seedu.momentum.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.momentum.commons.core.GuiSettings;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Project> PREDICATE_SHOW_ALL_PROJECTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

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
     * Returns true if a project with the same identity as {@code project} exists in the project book.
     */
    boolean hasProject(Project project);

    /**
     * Deletes the given project.
     * The project must exist in the project book.
     */
    void deleteProject(Project target);

    /**
     * Adds the given project.
     * {@code project} must not already exist in the project book.
     */
    void addProject(Project project);

    /**
     * Replaces the given project {@code target} with {@code editedProject}.
     * {@code target} must exist in the project book.
     * The project identity of {@code editedProject} must not be the same as another existing project in the project
     * book.
     */
    void setProject(Project target, Project editedProject);

    /** Returns an unmodifiable view of the filtered project list */
    ObservableList<Project> getFilteredProjectList();

    /** Returns a list of projects whose timers are running */
    ObservableList<Project> getRunningTimers();

    /**
     * Adds the given project to the running timers list.
     * {@code project} must have a running timer.
     * */
    void addRunningTimer(Project project);

    /**
     * Removes the given project from the running timers list.
     * {@code project} must have a running timer.
     */
    void removeRunningTimer(Project project);

    /**
     * Updates the filter of the filtered project list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProjectList(Predicate<Project> predicate);

    /**
     * Orders the list of projects in a way given by the {@code sortType}.
     *
     * @throws NullPointerException if {@code sortType} is null.
     */
    void orderFilteredProjectList(SortType sortType, boolean isAscending);
}
