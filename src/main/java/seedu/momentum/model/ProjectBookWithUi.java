package seedu.momentum.model;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

public class ProjectBookWithUi extends ProjectBook {

    private final ViewMode viewMode;
    private final Project project;
    private final Predicate<TrackedItem> predicate;
    private final Comparator<TrackedItem> comparator;
    /**
     * Constructs a {@code ProjectBookWithUi}
     */
    public ProjectBookWithUi(ReadOnlyProjectBook projectBook, ViewMode viewMode, Project project,
                             Predicate<TrackedItem> predicate, Comparator<TrackedItem> comparator) {
        super(projectBook);
        this.viewMode = viewMode;
        this.project = project;
        this.predicate = predicate;
        this.comparator = comparator;
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

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other == this // short circuit if same object
                || (other instanceof ProjectBookWithUi // instanceof handles nulls
                && viewMode.equals(((ProjectBookWithUi) other).getViewMode())
                && ((project == null && ((ProjectBookWithUi) other).getProject() == null)
                || (project.equals(((ProjectBookWithUi) other).getProject())))));
                //&& predicate.equals(((ProjectBookWithUi) other).getPredicate())));
    }
}
