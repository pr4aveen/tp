package seedu.momentum.model.project.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.TypicalProjects.ALICE;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.project.Project;
import seedu.momentum.testutil.ProjectBuilder;

/**
 * Tests if the {@code Project}'s name can be compared to another {@code Project}'s name correctly.
 */
public class NameCompareTest {

    public static final Project AAA_NAME = new ProjectBuilder().withName("AAA")
            .withCreatedDate("2020-02-02")
            .build();
    public static final Project ZZZ_NAME = new ProjectBuilder().withName("ZZZ")
            .withCreatedDate("2020-02-02")
            .build();
    public static final Project SAME_NAME = new ProjectBuilder().withName("Alice Pauline")
            .withCreatedDate("2020-02-02")
            .build();
    public static final NameCompare NAME_COMPARATOR = new NameCompare();

    @Test
    public void test_compare() {

        // second project name is alphabetically later -> returns negative number
        assertTrue(NAME_COMPARATOR.compare(ALICE, ZZZ_NAME) < 0);

        // second project has same created date but alphabetically earlier -> returns positive number
        assertTrue(NAME_COMPARATOR.compare(ALICE, AAA_NAME) > 0);

        // first and second project have same name
        assertEquals(NAME_COMPARATOR.compare(ALICE, SAME_NAME), 0);

    }

    @Test
    public void equals() {
        // same object
        assertEquals(NAME_COMPARATOR, NAME_COMPARATOR);

        // different NameCompare objects
        assertEquals(NAME_COMPARATOR, new NameCompare());
    }
}
