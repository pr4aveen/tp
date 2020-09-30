package seedu.momentum.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.UniqueProjectList;

/**
 * Wraps all data at the project-book level
 * Duplicates are not allowed (by .isSameProject comparison)
 */
public class ProjectBook implements ReadOnlyProjectBook {

    private final UniqueProjectList projects;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        projects = new UniqueProjectList();
    }

    public ProjectBook() {}

    /**
     * Creates an ProjectBook using the Projects in the {@code toBeCopied}
     */
    public ProjectBook(ReadOnlyProjectBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the project list with {@code projects}.
     * {@code projects} must not contain duplicate projects.
     */
    public void setProjects(List<Project> projects) {
        this.projects.setProjects(projects);
    }

    /**
     * Resets the existing data of this {@code ProjectBook} with {@code newData}.
     */
    public void resetData(ReadOnlyProjectBook newData) {
        requireNonNull(newData);

        setProjects(newData.getProjectList());
    }

    //// project-level operations

    /**
     * Returns true if a project with the same identity as {@code project} exists in the project book.
     */
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projects.contains(project);
    }

    /**
     * Adds a project to the project book.
     * The project must not already exist in the project book.
     */
    public void addProject(Project p) {
        projects.add(p);
    }

    /**
     * Replaces the given project {@code target} in the list with {@code editedProject}.
     * {@code target} must exist in the project book.
     * The project identity of {@code editedProject} must not be the same as another existing project in the project
     * book.
     */
    public void setProject(Project target, Project editedProject) {
        requireNonNull(editedProject);

        projects.setProject(target, editedProject);
    }

    /**
     * Removes {@code key} from this {@code ProjectBook}.
     * {@code key} must exist in the project book.
     */
    public void renameProject(Project key) {
        projects.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return projects.asUnmodifiableObservableList().size() + " projects";
        // TODO: refine later
    }

    @Override
    public ObservableList<Project> getProjectList() {
        return projects.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectBook // instanceof handles nulls
                && projects.equals(((ProjectBook) other).projects));
    }

    @Override
    public int hashCode() {
        return projects.hashCode();
    }
}
