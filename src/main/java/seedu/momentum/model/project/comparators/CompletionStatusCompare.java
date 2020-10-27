package seedu.momentum.model.project.comparators;

import java.util.Comparator;

import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.TrackedItem;

/**
 * Represents a comparator that compares the completion status of a project.
 */
public class CompletionStatusCompare implements Comparator<TrackedItem> {

    /**
     * Compares completion status of two tracked items.
     *
     * @param t1 first tracked item to compare.
     * @param t2 second tracked item to compare.
     * @return integer values for comparison.
     */
    public int compare(TrackedItem t1, TrackedItem t2) {
        CompletionStatus status1 = t1.getCompletionStatus();
        CompletionStatus status2 = t2.getCompletionStatus();
        return status1.compareTo(status2);
    }

}
