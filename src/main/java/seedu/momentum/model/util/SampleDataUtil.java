package seedu.momentum.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.UniqueItemList;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Contains utility methods for populating {@code ProjectBook} with sample data.
 */
public class SampleDataUtil {
    public static Project[] getSampleProjects() {
        return new Project[] {
            new Project(new Name("Flver art commision"),
                    new Description(""),
                    new CompletionStatus(),
                    new DateWrapper("2019-10-04"),
                    new Deadline("2020-10-04", new DateWrapper("2019-10-04")),
                    new Reminder("2022-06-22T01:01:21"),
                    getTagSet("flverr", "artcomm")),
            new Project(new Name("XYZ startup web UI design"),
                    new Description(""),
                    new CompletionStatus().reverse(),
                    new DateWrapper("2019-10-10"),
                    new Deadline("2020-10-10", "01:01:21", new DateWrapper("2019-10-10")),
                    new Reminder(),
                    getTagSet("important", "webdesign")),
            new Project(new Name("Blog design"),
                    new Description("For friend"),
                    new CompletionStatus(),
                    new DateWrapper("2019-06-22"),
                    new Deadline(),
                    new Reminder(),
                    getTagSet("webdesign")),
            new Project(new Name("Logo"),
                    new Description("Aunt Mary bakery"),
                    new CompletionStatus().reverse(),
                    new DateWrapper("2019-11-04"),
                    new Deadline("2020-11-04", "08:10:21", new DateWrapper("2019-11-04")),
                    new Reminder(),
                    getTagSet("otot", "logodesign")),
            new Project(new Name("Read up on new Illustrator Features"),
                    Description.EMPTY_DESCRIPTION,
                    new CompletionStatus().reverse(),
                    new DateWrapper("2019-10-24"),
                    new Deadline("2024-10-24", new DateWrapper("2019-10-24")),
                    new Reminder(),
                    getTagSet("selfimprovement", "important")),
            new Project(new Name("Think of a new Project"),
                    Description.EMPTY_DESCRIPTION,
                    new CompletionStatus(),
                    new DateWrapper("2019-01-04"),
                    new Deadline(),
                    new Reminder(),
                    getTagSet("brainstorming"))
        };
    }

    public static ReadOnlyProjectBook getSampleProjectBook() {
        ProjectBook sampleProjectBook = new ProjectBook();
        for (Project sampleProject : getSampleProjects()) {
            sampleProjectBook.addTrackedItem(sampleProject);
        }
        return sampleProjectBook;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a UniqueDurationList containing the list of strings given.
     */
    public static UniqueItemList<WorkDuration> getDurationList(WorkDuration... durations) {
        UniqueItemList<WorkDuration> durationList = new UniqueItemList<>();
        durationList.setItems(Arrays.asList(durations));
        return durationList;
    }

    /**
     * Returns a UniqueitemList containing the list of tasks given.
     */
    public static UniqueItemList<TrackedItem> getTaskList(TrackedItem... tasks) {
        UniqueItemList<TrackedItem> taskList = new UniqueItemList<>();
        taskList.setItems(Arrays.asList(tasks));
        return taskList;
    }

}
