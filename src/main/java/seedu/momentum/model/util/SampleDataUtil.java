package seedu.momentum.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.momentum.commons.core.Date;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.tag.Tag;
import seedu.momentum.model.timer.UniqueDurationList;
import seedu.momentum.model.timer.WorkDuration;

/**
 * Contains utility methods for populating {@code ProjectBook} with sample data.
 */
public class SampleDataUtil {
    public static Project[] getSampleProjects() {
        return new Project[]{
            new Project(new Name("Alex Yeoh"),
                    new Description("description"),
                    new Date("2019-10-04"),
                    new Deadline("2020-10-04", new Date("2019-10-04")),
                    getTagSet("friends")),
            new Project(new Name("Bernice Yu"),
                    new Description("description"),
                    new Date("2019-10-10"),
                    new Deadline("2020-10-10", "01:01:21", new Date("2019-10-10")),
                    getTagSet("colleagues", "friends")),
            new Project(new Name("Charlotte Oliveiro"),
                    new Description("description"),
                    new Date("2019-06-22"),
                    new Deadline(),
                    getTagSet("neighbours")),
            new Project(new Name("David Li"),
                    new Description("description"),
                    new Date("2019-11-04"),
                    new Deadline("2020-11-04", "08:10:21", new Date("2019-11-04")),
                    getTagSet("family")),
            new Project(new Name("Irfan Ibrahim"),
                    Description.EMPTY_DESCRIPTION,
                    new Date("2019-10-24"),
                    new Deadline("2020-10-24", new Date("2019-10-24")),
                    getTagSet("classmates")),
            new Project(new Name("Roy Balakrishnan"),
                    Description.EMPTY_DESCRIPTION,
                    new Date("2019-01-04"),
                    new Deadline(),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyProjectBook getSampleProjectBook() {
        ProjectBook sampleProjectBook = new ProjectBook();
        for (Project sampleProject : getSampleProjects()) {
            sampleProjectBook.addProject(sampleProject);
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
    public static UniqueDurationList getDurationList(WorkDuration... durations) {
        UniqueDurationList durationList = new UniqueDurationList();
        durationList.setDurations(Arrays.asList(durations));
        return durationList;
    }

}
