package seedu.momentum.model;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

/**
 * Represents a project book with additional information on how the data is displayed to the UI.
 */
public class ProjectBookWithUi extends ProjectBook {

    private final ViewMode viewMode;
    private final Project project;
    private final Predicate<TrackedItem> predicate;
    private final Comparator<TrackedItem> comparator;
    private final boolean isTagsVisible;
    private final ReadOnlyUserPrefs userPrefs;

    /**
     * Constructs a {@code ProjectBookWithUi}
     */
    public ProjectBookWithUi(ReadOnlyProjectBook projectBook, ViewMode viewMode, Project project,
                             Predicate<TrackedItem> predicate, Comparator<TrackedItem> comparator,
                             boolean isTagsVisible, ReadOnlyUserPrefs userPrefs) {
        super(projectBook);
        this.viewMode = viewMode;
        this.project = project;
        this.predicate = predicate;
        this.comparator = comparator;
        this.isTagsVisible = isTagsVisible;
        this.userPrefs = userPrefs;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    public Project getProject() {
        return project;
    }

    public Predicate<TrackedItem> getPredicate() {
        return predicate;
    }

    public Comparator<TrackedItem> getComparator() {
        return comparator;
    }

    public boolean isTagsVisible() {
        return isTagsVisible;
    }

    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other == this // short circuit if same object
                || (other instanceof ProjectBookWithUi // instanceof handles nulls
                && viewMode.equals(((ProjectBookWithUi) other).getViewMode())
                && ((project == null && ((ProjectBookWithUi) other).getProject() == null)
                || (project.equals(((ProjectBookWithUi) other).getProject())))
                && isTagsVisible == ((ProjectBookWithUi) other).isTagsVisible()))
                && userPrefs.equals(((ProjectBookWithUi) other).getUserPrefs());
        //&& predicate.equals(((ProjectBookWithUi) other).getPredicate())));

    }
}
