package seedu.momentum.testutil;

import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.project.Project;

/**
 * A utility class to help with building ProjectBook objects.
 * Example usage: <br>
 *     {@code ProjectBook projectBook = new ProjectBookBuilder().withProject("John", "Doe").build();}
 */
public class ProjectBookBuilder {

    private ProjectBook projectBook;

    public ProjectBookBuilder() {
        projectBook = new ProjectBook();
    }

    public ProjectBookBuilder(ProjectBook projectBook) {
        this.projectBook = projectBook;
    }

    /**
     * Adds a new {@code Project} to the {@code ProjectBook} that we are building.
     */
    public ProjectBookBuilder withProject(Project project) {
        projectBook.addTrackedItem(project);
        return this;
    }

    public ProjectBook build() {
        return projectBook;
    }
}
