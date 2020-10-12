package seedu.momentum.model;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.tag.Tag;

/**
 * Unmodifiable view of an project book
 */
public interface ReadOnlyProjectBook {

    /**
     * Returns an unmodifiable view of the projects list.
     * This list will not contain any duplicate projects.
     */
    ObservableList<Project> getProjectList();

    /**
     * Returns the collection of all tags that the user has entered for the projects.
     */
    Set<Tag> getProjectTags();
}
