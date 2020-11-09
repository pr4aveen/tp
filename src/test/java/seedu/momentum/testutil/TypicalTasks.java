//@@author pr4aveen
package seedu.momentum.testutil;

import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task THOMAS = new TaskBuilder().withName("Thomas Jefferson")
            .withDescription("Likes coding")
            .withCreatedDate("2000-11-05")
            .withDeadline("2020-11-05", "11:11:11", "2000-11-05")
            .withTags("friends").build();
    public static final Task ULYSSES = new TaskBuilder().withName("Ulysses Bob")
            .withDescription("Likes dogs")
            .withCreatedDate("2000-11-05")
            .withCompletionStatus(CompletionStatus.COMPLETED)
            .withDeadline("2020-11-05", "12:43:12", "2000-11-05")
            .withReminder("2030-12-02T01:21:31")
            .withTags("owesMoney", "friends")
            .withDurations(TypicalWorkDuration.DURATION_ONE_DAY)
            .withTimer(TypicalTimers.DAY).build();
    public static final Task VERONICA = new TaskBuilder().withName("Veronica Vinod")
            .withDescription("Likes poodles")
            .withCompletionStatus(CompletionStatus.COMPLETED)
            .withCreatedDate("2019-08-02")
            .withEmptyDeadline()
            .build();
    public static final Task WESLEY = new TaskBuilder().withName("Wesley Tan")
            .withDescription("Likes cats")
            .withCreatedDate("2019-05-21")
            .withEmptyDeadline()
            .withTags("friends").build();
    public static final Task XAVIER = new TaskBuilder().withName("Xavier Charles")
            .withDescription("Likes elephants")
            .withCreatedDate("2019-07-21")
            .withEmptyDeadline()
            .withDeadline("2020-07-21", "2019-07-21").build();
    public static final Task YVETTE = new TaskBuilder().withName("Yvette Cool")
            .withDescription("Likes starbucks")
            .withCreatedDate("2019-03-21")
            .withDeadline("2020-03-21", "05:02:09", "2019-03-21").build();
    public static final Task ZACH = new TaskBuilder().withName("Zach Smart")
            .withDescription("Likes you")
            .withCompletionStatus(CompletionStatus.COMPLETED)
            .withCreatedDate("2019-07-28")
            .withEmptyDeadline()
            .withReminder("2021-12-05T02:45:23")
            .build();

    private TypicalTasks() {
    }
}
