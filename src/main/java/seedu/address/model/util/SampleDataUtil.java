package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.project.Name;
import seedu.address.model.project.Project;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Project[] getSampleProjects() {
        return new Project[] {
            new Project(new Name("Alex Yeoh"), getTagSet("friends")),
            new Project(new Name("Bernice Yu"), getTagSet("colleagues", "friends")),
            new Project(new Name("Charlotte Oliveiro"), getTagSet("neighbours")),
            new Project(new Name("David Li"), getTagSet("family")),
            new Project(new Name("Irfan Ibrahim"), getTagSet("classmates")),
            new Project(new Name("Roy Balakrishnan"), getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
