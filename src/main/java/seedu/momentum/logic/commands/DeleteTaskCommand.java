//@@author pr4aveen

package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

/**
 * Deletes tasks from a project in Momentum.
 */
public class DeleteTaskCommand extends DeleteCommand {

    private final Project projectToDeleteTaskFrom;

    /**
     * Creates a {@code DeleteTaskCommand} that deletes a task at the given index from the specified project.
     *
     * @param targetIndex Index of the task to be deleted.
     * @param project Project to delete the task from.
     */
    public DeleteTaskCommand(Index targetIndex, Project project) {
        super(targetIndex);
        this.projectToDeleteTaskFrom = project;
    }

    /**
     * Deletes the task from the project in the provided model.
     *
     * @param model {@code Model} containing the proejct from which to delete the task.
     * @return Feedback message of the result of deleting the task, for display.
     * @throws CommandException If an error occurs during the deletion.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<TrackedItem> lastShownList = model.getDisplayList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToDelete = lastShownList.get(targetIndex.getZeroBased());
        Project projectAfterDeleteTask = projectToDeleteTaskFrom.deleteTask(trackedItemToDelete);
        model.setTrackedItem(projectToDeleteTaskFrom, projectAfterDeleteTask);
        model.viewTasks(projectAfterDeleteTask);

        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, trackedItemToDelete));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTaskCommand) other).targetIndex) // state check
                && projectToDeleteTaskFrom.equals(((DeleteTaskCommand) other).projectToDeleteTaskFrom)); // state check
    }
}
