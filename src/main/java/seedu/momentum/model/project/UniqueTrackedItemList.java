package seedu.momentum.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.momentum.model.project.comparators.CreatedDateCompare;
import seedu.momentum.model.project.comparators.DeadlineCompare;
import seedu.momentum.model.project.comparators.NameCompare;
import seedu.momentum.model.project.exceptions.DuplicateProjectException;
import seedu.momentum.model.project.exceptions.ProjectNotFoundException;

/**
 * A list of projects that enforces uniqueness between its elements and does not allow nulls.
 * A project is considered unique by comparing using {@code Project#isSameProject(Project)}. As such, adding and
 * updating of projects uses Project#isSameProject(Project) for equality so as to ensure that the project being added or
 * updated is unique in terms of identity in the UniqueProjectList. However, the removal of a project uses
 * Project#equals(Object) so as to ensure that the project with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Project#isSameProject(Project)
 */
public class UniqueProjectList implements Iterable<Project> {

    public static final SortType DEFAULT_SORT_TYPE = SortType.ALPHA;
    private SortType sortType = DEFAULT_SORT_TYPE;
    private final ObservableList<Project> internalList = FXCollections.observableArrayList();
    private final ObservableList<Project> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent project as the given argument.
     */
    public boolean contains(Project toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProject);
    }

    /**
     * Adds a project to the list.
     * The project must not already exist in the list.
     */
    public void add(Project toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProjectException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the project {@code target} in the list with {@code editedProject}.
     * {@code target} must exist in the list.
     * The project identity of {@code editedProject} must not be the same as another existing project in the list.
     */
    public void setProject(Project target, Project editedProject) {
        requireAllNonNull(target, editedProject);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProjectNotFoundException();
        }

        if (!target.isSameProject(editedProject) && contains(editedProject)) {
            throw new DuplicateProjectException();
        }

        internalList.set(index, editedProject);
    }

    /**
     * Removes the equivalent project from the list.
     * The project must exist in the list.
     */
    public void remove(Project toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProjectNotFoundException();
        }
    }

    public void setProjects(UniqueProjectList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code projects}.
     * {@code projects} must not contain duplicate projects.
     */
    public void setProjects(List<Project> projects) {
        requireAllNonNull(projects);
        if (!projectsAreUnique(projects)) {
            throw new DuplicateProjectException();
        }

        internalList.setAll(projects);
    }

    /**
     * Sets the order of the list of projects according to given {@code sortType} and {@code isAscending}.
     *
     * @param sortType type of sort.
     * @param isAscending order of sort.
     */
    public void setOrder(SortType sortType, boolean isAscending) {

        requireNonNull(sortType);

        switch (sortType) {
        case ALPHA:
            setOrderAlphaType(isAscending);
            break;
        case DEADLINE:
            setOrderDeadlineType(isAscending);
            break;
        case CREATED:
            setOrderCreatedDateType(isAscending);
            break;
        case NULL:
            setOrderNullType(isAscending);
            break;
        default:
            // Will always be one of the above. Default does nothing.
            break;
        }
    }

    /**
     * Sets the order of list of projects by alphabetical order, ascending or descending based on user input.
     *
     * @param isAscending order of sort specified by user.
     */
    private void setOrderAlphaType(boolean isAscending) {
        Comparator<Project> nameCompare = new NameCompare();
        nameCompare = isAscending ? nameCompare : nameCompare.reversed();
        sortType = SortType.ALPHA;
        internalList.sort(nameCompare);
    }

    /**
     * Sets the order of list of projects by deadline order, ascending or descending based on user input.
     *
     * @param isAscending order of sort specified by user.
     */
    private void setOrderDeadlineType(boolean isAscending) {
        Comparator<Project> nameCompare = new NameCompare();
        Comparator<HashMap<String, Object>> deadlineCompare = new DeadlineCompare();
        deadlineCompare = isAscending ? deadlineCompare : deadlineCompare.reversed();
        sortType = SortType.DEADLINE;
        internalList.sort(Comparator.comparing(Project::getNullOrDeadline, Comparator.nullsLast(deadlineCompare))
                .thenComparing(nameCompare));
    }

    /**
     * Sets the order of list of projects by created date order, ascending or descending based on user input.
     *
     * @param isAscending order of sort specified by user.
     */
    private void setOrderCreatedDateType(boolean isAscending) {
        Comparator<Project> createdDateCompare = new CreatedDateCompare();
        createdDateCompare = isAscending ? createdDateCompare : createdDateCompare.reversed();
        this.sortType = SortType.CREATED;
        internalList.sort(createdDateCompare);
    }

    /**
     * Sets the order of the list of projects to current sort type with specified order
     * if sort type has not been specified by user.
     *
     * @param isAscending order of sort specified by user.
     */
    private void setOrderNullType(boolean isAscending) {
        switch(this.sortType) {
        case ALPHA:
            setOrder(SortType.ALPHA, isAscending);
            break;
        case DEADLINE:
            setOrder(SortType.DEADLINE, isAscending);
            break;
        case CREATED:
            setOrder(SortType.CREATED, isAscending);
            break;
        default:
            // Will always be one of the above. Default does nothing.
            break;
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Project> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Project> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueProjectList // instanceof handles nulls
                        && internalList.equals(((UniqueProjectList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code projects} contains only unique projects.
     */
    private boolean projectsAreUnique(List<Project> projects) {
        for (int i = 0; i < projects.size() - 1; i++) {
            for (int j = i + 1; j < projects.size(); j++) {
                if (projects.get(i).isSameProject(projects.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
