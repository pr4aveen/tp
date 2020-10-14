package seedu.momentum.model.project.comparators;

import java.util.Comparator;

import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;

public class NameCompare implements Comparator<Project> {

    /**
     * Compares name of two projects.
     *
     * @param p1 first project to compare.
     * @param p2 second project to compare.
     * @return integer values for comparison.
     */
    public int compare(Project p1, Project p2) {
        Name p1Name = p1.getName();
        Name p2Name = p2.getName();
        return p1Name.compareTo(p2Name);
    }
}
