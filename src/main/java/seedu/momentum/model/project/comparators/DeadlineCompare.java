package seedu.momentum.model.project.comparators;

import java.util.Comparator;
import java.util.HashMap;

import seedu.momentum.commons.core.Date;
import seedu.momentum.commons.core.Time;
import seedu.momentum.model.project.Deadline;

/**
 * Compar
 */
public class DeadlineCompare implements Comparator<HashMap<String, Object>> {

    /**
     * Compares deadline of two projects.
     *
     * @param p1 first project to compare.
     * @param p2 second project to compare.
     * @return integer values for comparison.
     */
    public int compare(HashMap<String, Object> p1, HashMap<String, Object> p2) {
        Deadline p1Deadline = (Deadline) p1.get("deadline");
        Date p1DeadlineDate = p1Deadline.getDate();
        Deadline p2Deadline = (Deadline) p2.get("deadline");;
        Date p2DeadlineDate = p2Deadline.getDate();

        String p1Name = (String) p1.get("name");
        String p2Name = (String) p2.get("name");

        if (p1DeadlineDate.get().isBefore(p2DeadlineDate.get())) {
            return -1;
        } else if (p1DeadlineDate.get().isAfter(p2DeadlineDate.get())) {
            return 1;
        } else {
            if (!p1Deadline.hasTime() && p2Deadline.hasTime()) {
                return -1;
            } else if (p1Deadline.hasTime() && !p2Deadline.hasTime()) {
                return 1;
            } else if (p1Deadline.hasTime() && p2Deadline.hasTime()) {
                return bothHasTimeCompare(p1Deadline, p1Name, p2Deadline, p2Name);
            } else {
                return p1Name.compareTo(p2Name);
            }
        }
    }

    private int bothHasTimeCompare(Deadline p1Deadline, String p1Name, Deadline p2Deadline, String p2Name) {
        Time p1DeadlineTime = p1Deadline.getTime();
        Time p2DeadlineTime = p2Deadline.getTime();

        if (p1DeadlineTime.get().isBefore(p2DeadlineTime.get())) {
            return -1;
        } else if (p1DeadlineTime.get().isAfter(p2DeadlineTime.get())) {
            return 1;
        } else {
            return p1Name.compareTo(p2Name);
        }
    }
}
