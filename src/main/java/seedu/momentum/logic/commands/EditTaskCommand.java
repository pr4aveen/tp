package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

public class EditTaskCommand extends EditCommand {

    private final Project parentProject;

    /**
     * Create a EditTaskCommand that edits a task.
     *
     * @param index                     of the project in the filtered project list to edit.
     * @param editTrackedItemDescriptor details to edit the project with.
     * @param parentProject             The parent project of the task to edit.
     */
    public EditTaskCommand(Index index, EditTrackedItemDescriptor editTrackedItemDescriptor, Project parentProject) {
        super(index, editTrackedItemDescriptor);
        requireNonNull(parentProject);
        this.parentProject = parentProject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<TrackedItem> lastShownList = model.getDisplayList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToEdit = lastShownList.get(index.getZeroBased());
        TrackedItem editedTrackedItem = createEditedTrackedItem(trackedItemToEdit, editTrackedItemDescriptor, model);

        if (!trackedItemToEdit.isSameAs(editedTrackedItem) && model.hasTrackedItem(editedTrackedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        try {
            Project projectAfterEditTask = parentProject.setTask(trackedItemToEdit, editedTrackedItem);
            model.setTrackedItem(parentProject, projectAfterEditTask);
            model.viewTasks(projectAfterEditTask);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.rescheduleReminders();
        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_EDIT_PROJECT_SUCCESS, editedTrackedItem));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTrackedItemDescriptor.equals(e.editTrackedItemDescriptor)
                && parentProject.equals(e.parentProject);
    }
}
