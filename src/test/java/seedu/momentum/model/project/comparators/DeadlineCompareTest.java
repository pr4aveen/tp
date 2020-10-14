package seedu.momentum.model.project.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.TypicalProjects.ALICE;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.project.Project;
import seedu.momentum.testutil.ProjectBuilder;

/**
 * Tests if the {@code Project}'s deadline can be compared to another {@code Project}'s deadline created correctly.
 */
public class DeadlineCompareTest {

    public static final Project ANCIENT_DEADLINE_PROJECT = new ProjectBuilder().withName("Egyptian Pyramid")
            .withCreatedDate("0001-01-01")
            .withDeadline("0001-02-02")
            .build();
    public static final Project FUTURE_DEADLINE_PROJECT = new ProjectBuilder().withName("UFO")
            .withCreatedDate("3000-01-01")
            .withDeadline("3000-02-02")
            .build();
    public static final Project SAME_DATE_NO_TIME = new ProjectBuilder().withName("AAA")
            .withCreatedDate("2019-11-05")
            .withDeadline("2020-11-05")
            .build();
    public static final Project SAME_DATE_EARLIER_TIME = new ProjectBuilder().withName("AAA")
            .withCreatedDate("2019-11-05")
            .withDeadline("2020-11-05", "01:01:01")
            .build();
    public static final Project SAME_DATE_LATER_TIME = new ProjectBuilder().withName("AAA")
            .withCreatedDate("2019-11-05")
            .withDeadline("2020-11-05", "23:23:23")
            .build();
    public static final Project SAME_DEADLINE_NAME_ALPHABETICALLY_EARLIER = new ProjectBuilder().withName("AAA")
            .withCreatedDate("2019-11-05")
            .withDeadline("2020-11-05", "11:11:11")
            .build();
    public static final Project SAME_DEADLINE_NAME_ALPHABETICALLY_LATER = new ProjectBuilder().withName("ZZZ")
            .withCreatedDate("2019-11-05")
            .withDeadline("2020-11-05", "11:11:11")
            .build();
    public static final Project SAME_DEADLINE_NAME_ALPHABETICALLY_SAME = new ProjectBuilder()
            .withName("Alice Pauline")
            .withDeadline("2020-11-05", "11:11:11")
            .withCreatedDate("2019-11-05")
            .build();
    public static final DeadlineCompare DEADLINE_COMPARATOR = new DeadlineCompare();

    @Test
    public void test_compare() {

        // second project has later deadline date -> returns -1
        assertEquals(DEADLINE_COMPARATOR.compare(ALICE.getNullOrDeadline(),
                FUTURE_DEADLINE_PROJECT.getNullOrDeadline()), -1);

        // second project has earlier deadline date -> returns 1
        assertEquals(DEADLINE_COMPARATOR.compare(ALICE.getNullOrDeadline(),
                ANCIENT_DEADLINE_PROJECT.getNullOrDeadline()), 1);

        // first project has no time, second project has time -> returns -1
        assertEquals(DEADLINE_COMPARATOR.compare(SAME_DATE_NO_TIME.getNullOrDeadline(),
                ALICE.getNullOrDeadline()), -1);

        // first project has time, second project has no time -> returns 1
        assertEquals(DEADLINE_COMPARATOR.compare(ALICE.getNullOrDeadline(),
                SAME_DATE_NO_TIME.getNullOrDeadline()), 1);

        // both projects have time and second project has later time -> returns -1
        assertEquals(DEADLINE_COMPARATOR.compare(ALICE.getNullOrDeadline(), SAME_DATE_LATER_TIME.getNullOrDeadline()),
                -1);

        // both projects have time and second project has earlier time -> returns 1
        assertEquals(DEADLINE_COMPARATOR.compare(ALICE.getNullOrDeadline(),
                SAME_DATE_EARLIER_TIME.getNullOrDeadline()), 1);

        // both projects have same date and time and second project name is alphabetically later
        // -> returns negative number
        assertTrue(DEADLINE_COMPARATOR.compare(ALICE.getNullOrDeadline(),
                SAME_DEADLINE_NAME_ALPHABETICALLY_LATER.getNullOrDeadline()) < 0);

        // both projects have same date and time and second project name is alphabetically earlier
        // -> returns positive number
        assertTrue(DEADLINE_COMPARATOR.compare(ALICE.getNullOrDeadline(),
                SAME_DEADLINE_NAME_ALPHABETICALLY_EARLIER.getNullOrDeadline()) > 0);

        // both projects have same date and time name
        // -> returns zero
        assertEquals(DEADLINE_COMPARATOR.compare(ALICE.getNullOrDeadline(),
                SAME_DEADLINE_NAME_ALPHABETICALLY_SAME.getNullOrDeadline()), 0);

    }

}
