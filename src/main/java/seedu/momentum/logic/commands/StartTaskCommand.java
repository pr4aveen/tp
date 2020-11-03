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
 * Starts a timer to track a task in Momentum.
 */
public class StartTaskCommand extends StartCommand {

    private final Project parentProject;

    /**
     * Creates a StartTaskCommand that starts the timer for a task.
     *
     * @param targetIndex The index of the task to start.
     * @param parentProject The parent project of the task.
     */
    public StartTaskCommand(Index targetIndex, Project parentProject) {
        super(targetIndex);
        requireNonNull(parentProject);
        this.parentProject = parentProject;
    }

    /**
     * Starts the timer for the task in the provided model.
     *
     * @param model {@code Model} containing the task whose timer to start.
     * @return feedback message of timer result, for display
     * @throws CommandException If an error occurs when starting the timer.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<TrackedItem> lastShownList = model.getDisplayList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToStart = lastShownList.get(targetIndex.getZeroBased());

        if (trackedItemToStart.isRunning()) {
            throw new CommandException(MESSAGE_EXISTING_TIMER_ERROR);
        }

        TrackedItem newTrackedItem = trackedItemToStart.startTimer();
        Project newProject = parentProject.setTask(trackedItemToStart, newTrackedItem);
        model.setTrackedItem(parentProject, newProject);

        model.rescheduleReminders();
        model.commitToHistory();

        return new CommandResult(String.format(MESSAGE_START_TIMER_SUCCESS, targetIndex.getOneBased())
                + newTrackedItem.getTimer().getStartTime().getFormatted());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTaskCommand // instanceof handles nulls
                && targetIndex.equals(((StartTaskCommand) other).targetIndex)
                && parentProject.equals(((StartTaskCommand) other).parentProject)); // state check
    }
}
