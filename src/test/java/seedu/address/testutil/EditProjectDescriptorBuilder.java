package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.project.Email;
import seedu.address.model.project.Name;
import seedu.address.model.project.Phone;
import seedu.address.model.project.Project;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditProjectDescriptor objects.
 */
public class EditProjectDescriptorBuilder {

    private EditCommand.EditProjectDescriptor descriptor;

    public EditProjectDescriptorBuilder() {
        descriptor = new EditCommand.EditProjectDescriptor();
    }

    public EditProjectDescriptorBuilder(EditCommand.EditProjectDescriptor descriptor) {
        this.descriptor = new EditCommand.EditProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditProjectDescriptor} with fields containing {@code project}'s details
     */
    public EditProjectDescriptorBuilder(Project project) {
        descriptor = new EditCommand.EditProjectDescriptor();
        descriptor.setName(project.getName());
        descriptor.setPhone(project.getPhone());
        descriptor.setEmail(project.getEmail());
        descriptor.setTags(project.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditProjectDescriptor} that we are building.
     */
    public EditProjectDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditProjectDescriptor}
     * that we are building.
     */
    public EditProjectDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditProjectDescriptor build() {
        return descriptor;
    }
}
