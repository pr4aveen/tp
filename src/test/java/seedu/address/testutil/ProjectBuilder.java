package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.project.Email;
import seedu.address.model.project.Name;
import seedu.address.model.project.Phone;
import seedu.address.model.project.Project;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Project objects.
 */
public class ProjectBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";

    private Name name;
    private Phone phone;
    private Email email;
    private Set<Tag> tags;

    /**
     * Creates a {@code ProjectBuilder} with the default details.
     */
    public ProjectBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ProjectBuilder with the data of {@code projectToCopy}.
     */
    public ProjectBuilder(Project projectToCopy) {
        name = projectToCopy.getName();
        phone = projectToCopy.getPhone();
        email = projectToCopy.getEmail();
        tags = new HashSet<>(projectToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Project} that we are building.
     */
    public ProjectBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Project} that we are building.
     */
    public ProjectBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Project} that we are building.
     */
    public ProjectBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Project} that we are building.
     */
    public ProjectBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Project build() {
        return new Project(name, phone, email, tags);
    }

}
