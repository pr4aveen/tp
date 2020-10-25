package seedu.momentum.testutil;

import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.momentum.logic.commands.AddCommand;
import seedu.momentum.logic.commands.EditCommand;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.tag.Tag;

/**
 * A utility class for Project.
 */
public class ProjectUtil {

    /**
     * Returns an add command string for adding the {@code project}.
     */
    public static String getAddCommand(Project project) {
        return AddCommand.COMMAND_WORD + " " + getProjectDetails(project);
    }

    /**
     * Returns the part of command string for the given {@code project}'s details.
     */
    public static String getProjectDetails(Project project) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + project.getName().fullName + " ");
        sb.append(PREFIX_DESCRIPTION + project.getDescription().value + " ");
        if (project.getCompletionStatus().isCompleted()) {
            sb.append(PREFIX_COMPLETION_STATUS + " ");
        }
        Deadline deadline = project.getDeadline();
        if (!deadline.isEmpty()) {
            sb.append(PREFIX_DEADLINE_DATE + deadline.getDate().toString() + " ");
            if (deadline.hasTime()) {
                sb.append(PREFIX_DEADLINE_TIME + deadline.getTime().toString() + " ");
            }
        }
        project.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTrackedItemDescriptor}'s details.
     */
    public static String getEditProjectDescriptorDetails(EditCommand.EditTrackedItemDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION).append(description.value)
                .append(" "));
        descriptor.getCompletionStatus().ifPresent(editedCompletionStatus
            -> sb.append(PREFIX_COMPLETION_STATUS).append(" "));
        if (descriptor.getDeadline().isPresent() && !descriptor.getDeadline().isEmpty()) {
            Deadline deadline = descriptor.getDeadline().get();
            sb.append(PREFIX_DEADLINE_DATE + deadline.getDate().toString() + " ");
            if (deadline.hasTime()) {
                sb.append(PREFIX_DEADLINE_TIME + deadline.getTime().toString() + " ");
            }
        }
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
