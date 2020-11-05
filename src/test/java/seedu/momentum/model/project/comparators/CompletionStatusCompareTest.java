package seedu.momentum.model.project.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Project;
import seedu.momentum.testutil.ProjectBuilder;

public class CompletionStatusCompareTest {

    public static final Project PROJECT_COMPLETED = new ProjectBuilder().withName("COMPLETED")
            .withCreatedDate("2020-02-02")
            .withCompletionStatus(CompletionStatus.COMPLETED)
            .build();
    public static final Project PROJECT_NOT_COMPLETED = new ProjectBuilder().withName("INCOMPLETE")
            .withCreatedDate("2020-02-02")
            .build();
    public static final CompletionStatusCompare COMPLETION_STATUS_COMPARE = new CompletionStatusCompare();

    @Test
    public void test_compare() {

        // first project name is completed, second is not -> returns negative number
        assertTrue(COMPLETION_STATUS_COMPARE.compare(PROJECT_COMPLETED, PROJECT_NOT_COMPLETED) > 0);

        // second project name is completed, first is not -> returns negative number
        assertTrue(COMPLETION_STATUS_COMPARE.compare(PROJECT_NOT_COMPLETED, PROJECT_COMPLETED) < 0);

        // first and second project both not completed
        Project projectNotCompleted = new ProjectBuilder().withName("INCOMPLETE TWO")
                .withCreatedDate("2020-02-02")
                .withCompletionStatus(new CompletionStatus())
                .build();
        assertEquals(COMPLETION_STATUS_COMPARE.compare(PROJECT_NOT_COMPLETED, projectNotCompleted), 0);

        // first and second project both completed
        Project projectCompleted = new ProjectBuilder().withName("COMPLETE TWO")
                .withCreatedDate("2020-02-02")
                .withCompletionStatus(new CompletionStatus().reverse())
                .build();
        assertEquals(COMPLETION_STATUS_COMPARE.compare(projectCompleted, PROJECT_COMPLETED), 0);

    }
}
