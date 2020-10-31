package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

public class DeleteTaskCommand extends DeleteCommand {

    private final Project projectToDeleteTaskFrom;

    /**
     * Deletes a task at a given index from a certain project.
     *
     * @param targetIndex index of the task to be deleted.
     * @param project project to delete the task from.
     */
    public DeleteTaskCommand(Index targetIndex, Project project) {
        super(targetIndex);
        this.projectToDeleteTaskFrom = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<TrackedItem> lastShownList = model.getDisplayList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToDelete = lastShownList.get(targetIndex.getZeroBased());
        Project projectAfterDeleteTask = projectToDeleteTaskFrom.deleteTask(trackedItemToDelete);
        model.setTrackedItem(projectToDeleteTaskFrom, projectAfterDeleteTask);
        model.viewTasks(projectAfterDeleteTask);

        model.rescheduleReminders();
        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, trackedItemToDelete));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTaskCommand) other).targetIndex) // state check
                && projectToDeleteTaskFrom.equals(((DeleteTaskCommand) other).projectToDeleteTaskFrom)); // state check
    }
}
