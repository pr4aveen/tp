package seedu.momentum.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ReadOnlyProjectBook;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ProjectBook} with sample data.
 */
public class SampleDataUtil {
    public static Project[] getSampleProjects() {
        return new Project[] {
            new Project(new Name("Alex Yeoh"), new Description("description"), getTagSet("friends")),
            new Project(new Name("Bernice Yu"), new Description("description"), getTagSet("colleagues", "friends")),
            new Project(new Name("Charlotte Oliveiro"), new Description("description"), getTagSet("neighbours")),
            new Project(new Name("David Li"), new Description("description"), getTagSet("family")),
            new Project(new Name("Irfan Ibrahim"), Description.EMPTY_DESCRIPTION, getTagSet("classmates")),
            new Project(new Name("Roy Balakrishnan"), Description.EMPTY_DESCRIPTION, getTagSet("colleagues"))
        };
    }

    public static ReadOnlyProjectBook getSampleProjectBook() {
        ProjectBook sampleAb = new ProjectBook();
        for (Project sampleProject : getSampleProjects()) {
            sampleAb.addProject(sampleProject);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
