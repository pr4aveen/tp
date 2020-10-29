package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

/**
 * Deletes a project identified using it's displayed index from the project book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the project identified by the index number used in the displayed project list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Deleted Project: %1$s";

    private final Index targetIndex;
    private Project projectToDeleteTaskFrom;

    /**
     * Deletes a project at a given index from the project book.
     *
     * @param targetIndex index of the project to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Deletes a task at a given index from a certain project.
     *
     * @param targetIndex index of the task to be deleted.
     * @param project project to delete the task from.
     */
    public DeleteCommand(Index targetIndex, Project project) {
        this.targetIndex = targetIndex;
        this.projectToDeleteTaskFrom = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ViewMode viewMode = model.getViewMode();

        List<TrackedItem> lastShownList = viewMode == ViewMode.PROJECTS
                ? model.getFilteredTrackedItemList()
                : projectToDeleteTaskFrom.getTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (viewMode == ViewMode.PROJECTS) {
            model.deleteTrackedItem(trackedItemToDelete);
        } else {
            Project projectBeforeDeleteTask = projectToDeleteTaskFrom;
            Project projectAfterDeleteTask = projectToDeleteTaskFrom.deleteTask(trackedItemToDelete);
            model.setTrackedItem(ViewMode.TASKS, projectBeforeDeleteTask, projectAfterDeleteTask);
            model.viewTasks(projectAfterDeleteTask);
        }
        model.rescheduleReminders();
        model.commitToHistory(false);
        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, trackedItemToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
