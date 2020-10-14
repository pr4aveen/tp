package seedu.momentum.model.project.comparators;

import java.util.Comparator;

import seedu.momentum.commons.core.Date;
import seedu.momentum.model.project.Project;

/**
 * Compares date created of two projects.
 */
public class CreatedDateCompare implements Comparator<Project> {

    /**
     * Compares the date created of two projects.
     *
     * @param p1 first project to compare.
     * @param p2 second project to compare.
     * @return integer values for comparison.
     */
    public int compare(Project p1, Project p2) {

        Date p1Date = p1.getCreatedDate();
        Date p2Date = p2.getCreatedDate();

        NameCompare nameCompare = new NameCompare();

        if (p1Date.get().isBefore(p2Date.get())) {
            return -1;
        } else if (p1Date.get().isAfter(p2Date.get())) {
            return 1;
        } else {
            return nameCompare.compare(p1, p2);
        }
    }
}
