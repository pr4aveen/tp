package seedu.momentum.model;

import seedu.momentum.model.project.Project;

public class ProjectBookWithUi extends ProjectBook {

    private final ViewMode viewMode;
    private final Project project;
    /**
     * Constructs a {@code ProjectBookWithUi}
     */
    public ProjectBookWithUi(ReadOnlyProjectBook projectBook, ViewMode viewMode, Project project) {
        super(projectBook);
        this.viewMode = viewMode;
        this.project = project;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    public Project getProject() {
        return project;
    }

}
