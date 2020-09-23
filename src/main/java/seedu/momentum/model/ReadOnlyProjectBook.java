package seedu.momentum.model;

import javafx.collections.ObservableList;
import seedu.momentum.model.project.Project;

/**
 * Unmodifiable view of an project book
 */
public interface ReadOnlyProjectBook {

    /**
     * Returns an unmodifiable view of the projects list.
     * This list will not contain any duplicate projects.
     */
    ObservableList<Project> getProjectList();

}
