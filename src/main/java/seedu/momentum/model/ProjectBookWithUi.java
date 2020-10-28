package seedu.momentum.model;

import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

public class ProjectBookWithUi extends ProjectBook {

    private final ViewMode viewMode;
    private final boolean isPreviousCommandTimer;
    private final Project project;
    private final TrackedItem runningTimer;
    private final boolean toAdd;

    /**
     * Constructs a {@code ProjectBookWithUi}
     */
    public ProjectBookWithUi(ReadOnlyProjectBook projectBook, ViewMode viewMode, boolean isPreviousCommandTimer,
                             Project project, TrackedItem runningTimer, boolean toAdd) {
        super(projectBook);
        this.viewMode = viewMode;
        this.isPreviousCommandTimer = isPreviousCommandTimer;
        this.project = project;
        this.runningTimer = runningTimer;
        this.toAdd = toAdd;
    }


    public ViewMode getViewMode() {
        return viewMode;
    }

    public boolean getIsPreviousCommandTimer() {
        return isPreviousCommandTimer;
    }

    public Project getProject() {
        return project;
    }

    public TrackedItem getRunningTimer() {
        return runningTimer;
    }

    public boolean getToAdd() {
        return toAdd;
    }
}
