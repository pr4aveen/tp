package seedu.momentum.model.project.comparators;

import java.util.Comparator;
import java.util.HashMap;

import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Name;

/**
 * Compares deadline of two tracked items.
 */
public class DeadlineCompare implements Comparator<HashMap<String, Object>> {

    /**
     * Compares deadline of two tracked items.
     *
     * @param p1 first tracked item to compare.
     * @param p2 second tracked item to compare.
     * @return integer values for comparison.
     */
    public int compare(HashMap<String, Object> p1, HashMap<String, Object> p2) {
        Deadline p1Deadline = (Deadline) p1.get("deadline");
        Deadline p2Deadline = (Deadline) p2.get("deadline");;

        Name p1Name = (Name) p1.get("name");
        Name p2Name = (Name) p2.get("name");

        int deadlineCompareToValue = p1Deadline.compareTo(p2Deadline);

        if (deadlineCompareToValue == 0) {
            return p1Name.compareTo(p2Name);
        }
        return deadlineCompareToValue;
    }
}
