package seedu.momentum.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.logic.commands.EditCommand;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;

/**
 * A utility class to help with building EditTrackedItemDescriptor objects.
 */
public class EditTrackedItemDescriptorBuilder {

    private EditCommand.EditTrackedItemDescriptor descriptor;

    public EditTrackedItemDescriptorBuilder() {
        descriptor = new EditCommand.EditTrackedItemDescriptor();
    }

    public EditTrackedItemDescriptorBuilder(EditCommand.EditTrackedItemDescriptor descriptor) {
        this.descriptor = new EditCommand.EditTrackedItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTrackedItemDescriptor} with fields containing {@code trackedItem}'s details
     */
    public EditTrackedItemDescriptorBuilder(TrackedItem trackedItem) {
        descriptor = new EditCommand.EditTrackedItemDescriptor();
        descriptor.setName(trackedItem.getName());
        descriptor.setCompletionStatus(trackedItem.getCompletionStatus());
        descriptor.setDescription(trackedItem.getDescription());
        descriptor.setDeadline(trackedItem.getDeadline());
        descriptor.setReminder(trackedItem.getReminder());
        descriptor.setTags(trackedItem.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTrackedItemDescriptor} that we are building.
     */
    public EditTrackedItemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditTrackedItemDescriptor} that we are building.
     */
    public EditTrackedItemDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code CompletionStatus} of the {@code EditTrackedItemDescriptor} that we are building.
     */
    public EditTrackedItemDescriptorBuilder withCompletionStatus(CompletionStatus completionStatus) {
        descriptor.setCompletionStatus(completionStatus);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditTrackedItemDescriptor} that we are building.
     */
    public EditTrackedItemDescriptorBuilder withDeadline(String date, String createdDate) {
        descriptor.setDeadline(new Deadline(date, new DateWrapper(createdDate)));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditTrackedItemDescriptor} that we are building.
     */
    public EditTrackedItemDescriptorBuilder withDeadline(String date, String time, String createDate) {
        descriptor.setDeadline(new Deadline(date, time, new DateWrapper(createDate)));
        return this;
    }

    /**
     * Sets the {@code Reminder} of the {@code EditTrackedItemDescriptor} that we are building.
     */
    public EditTrackedItemDescriptorBuilder withReminder(String dateTime, String createdDate) {
        descriptor.setReminder(new Reminder(dateTime, new DateWrapper(createdDate)));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTrackedItemDescriptor}
     * that we are building.
     */
    public EditTrackedItemDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditTrackedItemDescriptor build() {
        return descriptor;
    }
}
