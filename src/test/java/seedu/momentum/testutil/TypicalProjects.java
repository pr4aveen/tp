package seedu.momentum.testutil;

import static seedu.momentum.logic.commands.CommandTestUtil.VALID_COMPLETION_STATUS_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_CREATED_DATE_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_CREATED_DATE_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_DATE_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_TIME_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_REMINDER_AMY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Project;

/**
 * A utility class containing a list of {@code Project} objects to be used in tests.
 */
public class TypicalProjects {
    public static final Project ALICE = new ProjectBuilder().withName("Alice Pauline")
            .withDescription("Likes coding")
            .withCreatedDate("2000-11-05")
            .withDeadline("2020-11-05", "11:11:11", "2000-11-05")
            .withTags("friends").build();
    public static final Project BENSON = new ProjectBuilder().withName("Benson Meier")
            .withDescription("Likes dogs")
            .withCreatedDate("2000-11-05")
            .withCompletionStatus(CompletionStatus.COMPLETED)
            .withDeadline("2020-11-05", "12:43:12", "2000-11-05")
            .withReminder("2030-12-02T01:21:31", "2000-11-05")
            .withTags("owesMoney", "friends")
            .withDurations(TypicalWorkDuration.DURATION_ONE_DAY)
            .withTimer(TypicalTimers.DAY).build();
    public static final Project CARL = new ProjectBuilder().withName("Carl Kurz")
            .withDescription("Likes poodles")
            .withCompletionStatus(CompletionStatus.COMPLETED)
            .withCreatedDate("2019-08-02")
            .withEmptyDeadline()
            .build();
    public static final Project DANIEL = new ProjectBuilder().withName("Daniel Meier")
            .withDescription("Likes cats")
            .withCreatedDate("2019-05-21")
            .withEmptyDeadline()
            .withTags("friends").build();
    public static final Project ELLE = new ProjectBuilder().withName("Elle Meyer")
            .withDescription("Likes elephants")
            .withCreatedDate("2019-07-21")
            .withEmptyDeadline()
            .withDeadline("2020-07-21", "2019-07-21").build();
    public static final Project FIONA = new ProjectBuilder().withName("Fiona Kunz")
            .withDescription("Likes starbucks")
            .withCreatedDate("2019-03-21")
            .withDeadline("2020-03-21", "05:02:09", "2019-03-21").build();
    public static final Project GEORGE = new ProjectBuilder().withName("George Best")
            .withDescription("Likes you")
            .withCompletionStatus(CompletionStatus.COMPLETED)
            .withCreatedDate("2019-07-28")
            .withEmptyDeadline()
            .withReminder("2021-12-05T02:45:23", "2019-07-28")
            .build();

    // Manually added
    public static final Project HOON = new ProjectBuilder().withName("Hoon Meier")
            .build();
    public static final Project IDA = new ProjectBuilder().withName("Ida Mueller")
            .build();

    // Manually added - Project's details found in {@code CommandTestUtil}
    public static final Project AMY = new ProjectBuilder().withName(VALID_NAME_AMY)
            .withDescription(VALID_DESCRIPTION_AMY)
            .withCreatedDate(VALID_CREATED_DATE_AMY)
            .withDeadline(VALID_DEADLINE_DATE_AMY, VALID_DEADLINE_TIME_AMY, VALID_CREATED_DATE_AMY)
            .withReminder(VALID_REMINDER_AMY, VALID_CREATED_DATE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Project BOB = new ProjectBuilder().withName(VALID_NAME_BOB)
            .withDescription(VALID_DESCRIPTION_BOB)
            .withCompletionStatus(VALID_COMPLETION_STATUS_BOB)
            .withCreatedDate(VALID_CREATED_DATE_BOB)
            .withDeadline(VALID_DEADLINE_DATE_BOB, VALID_CREATED_DATE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    private TypicalProjects() {
    } // prevents instantiation

    /**
     * Returns an {@code ProjectBook} with all the typical projects.
     */
    public static ProjectBook getTypicalProjectBook() {
        ProjectBook projectBook = new ProjectBook();
        for (Project project : getTypicalProjects()) {
            projectBook.addTrackedItem(project);
        }
        return projectBook;
    }

    public static List<Project> getTypicalProjects() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
