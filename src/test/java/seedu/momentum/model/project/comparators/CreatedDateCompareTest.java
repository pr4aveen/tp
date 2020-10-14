package seedu.momentum.model.project.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.TypicalProjects.ALICE;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.project.Project;
import seedu.momentum.testutil.ProjectBuilder;

/**
 * Tests if the {@code Project}'s date created can be compared to another {@code Project}'s date created correctly.
 */
public class CreatedDateCompareTest {

    public static final Project ANCIENT_DATE_PROJECT = new ProjectBuilder().withName("Egyptian Pyramid")
            .withCreatedDate("0001-01-01")
            .build();
    public static final Project FUTURE_DATE_PROJECT = new ProjectBuilder().withName("UFO")
            .withCreatedDate("3000-01-01")
            .build();
    public static final Project SAME_CREATED_DATE_NAME_ALPHABETICALLY_EARLIER = new ProjectBuilder().withName("AAA")
            .withCreatedDate("2019-11-05")
            .build();
    public static final Project SAME_CREATED_DATE_NAME_ALPHABETICALLY_LATER = new ProjectBuilder().withName("ZZZ")
            .withCreatedDate("2019-11-05")
            .build();
    public static final Project SAME_CREATED_DATE_NAME_ALPHABETICALLY_SAME = new ProjectBuilder()
            .withName("Alice Pauline")
            .withCreatedDate("2019-11-05")
            .build();
    public static final CreatedDateCompare CREATED_DATE_COMPARATOR = new CreatedDateCompare();

    @Test
    public void test_compare() {

        // second project has later created date -> returns -1
        assertEquals(CREATED_DATE_COMPARATOR.compare(ALICE, FUTURE_DATE_PROJECT), -1);

        // second project has earlier created date -> returns 1
        assertEquals(CREATED_DATE_COMPARATOR.compare(ALICE, ANCIENT_DATE_PROJECT), 1);

        // second project has same created date but alphabetically later -> returns negative number
        assertTrue(CREATED_DATE_COMPARATOR.compare(ALICE, SAME_CREATED_DATE_NAME_ALPHABETICALLY_LATER) < 0);

        // second project has same created date but alphabetically earlier -> returns positive number
        assertTrue(CREATED_DATE_COMPARATOR.compare(ALICE, SAME_CREATED_DATE_NAME_ALPHABETICALLY_EARLIER) > 0);

        // second project has same created date but alphabetically same -> returns 0
        assertEquals(CREATED_DATE_COMPARATOR.compare(ALICE, SAME_CREATED_DATE_NAME_ALPHABETICALLY_SAME), 0);

    }

}
