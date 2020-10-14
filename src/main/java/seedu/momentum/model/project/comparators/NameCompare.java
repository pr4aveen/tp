package seedu.momentum.model.project.comparators;

import java.util.Comparator;

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
        String p1Name = p1.getName().toString().toLowerCase();
        String p2Name = p2.getName().toString().toLowerCase();
        return p1Name.compareTo(p2Name);
    }
}
