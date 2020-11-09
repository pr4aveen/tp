//@@author kkangs0226
package seedu.momentum.model.project.comparators;

import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.model.project.TrackedItem;

/**
 * Compares created date of two tracked items.
 */
public class CreatedDateCompare implements Comparator<TrackedItem> {

    /**
     * Compares the created date of two tracked items.
     *
     * @param t1 First tracked item to compare.
     * @param t2 Second tracked item to compare.
     * @return Integer values for comparison.
     */
    public int compare(TrackedItem t1, TrackedItem t2) {

        requireAllNonNull(t1, t2);

        DateWrapper p1DateWrapper = t1.getCreatedDate();
        DateWrapper p2DateWrapper = t2.getCreatedDate();

        NameCompare nameCompare = new NameCompare();

        if (p1DateWrapper.get().isBefore(p2DateWrapper.get())) {
            return -1;
        } else if (p1DateWrapper.get().isAfter(p2DateWrapper.get())) {
            return 1;
        }
        return nameCompare.compare(t1, t2);
    }
}
