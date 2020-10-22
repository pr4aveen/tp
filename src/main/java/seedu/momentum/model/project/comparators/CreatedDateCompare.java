package seedu.momentum.model.project.comparators;

import java.util.Comparator;

import seedu.momentum.commons.core.Date;
import seedu.momentum.model.project.TrackedItem;

/**
 * Compares date created of two projects.
 */
public class CreatedDateCompare implements Comparator<TrackedItem> {

    /**
     * Compares the date created of two tracked item.
     *
     * @param t1 first tracked item to compare.
     * @param t2 second tracked item to compare.
     * @return integer values for comparison.
     */
    public int compare(TrackedItem t1, TrackedItem t2) {

        Date p1Date = t1.getCreatedDate();
        Date p2Date = t2.getCreatedDate();

        NameCompare nameCompare = new NameCompare();

        if (p1Date.get().isBefore(p2Date.get())) {
            return -1;
        } else if (p1Date.get().isAfter(p2Date.get())) {
            return 1;
        } else {
            return nameCompare.compare(t1, t2);
        }
    }
}
