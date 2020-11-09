//@@author kkangs0226
package seedu.momentum.model;

import java.util.Comparator;
import java.util.Objects;
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
     * Constructs a {@code ProjectBookWithUi}.
     *
     * @param projectBook Project book containing the initial data.
     * @param viewMode Current view mode.
     * @param project Current project in the model manager.
     * @param predicate Current predicate used to filter the list.
     * @param comparator Current comparator used to sort the list.
     * @param isTagsVisible Whether the tags window in the UI is present.
     * @param userPrefs Current user preferences in the application.
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
        if (other == this) {
            return true;
        }

        if (!(other instanceof ProjectBookWithUi)) {
            return false;
        }

        ProjectBookWithUi o = (ProjectBookWithUi) other;
        return super.equals(other)
                && viewMode.equals(o.getViewMode())
                && (Objects.equals(project, o.getProject()))
                && isTagsVisible == o.isTagsVisible()
                && userPrefs.equals(o.getUserPrefs());
    }
}
