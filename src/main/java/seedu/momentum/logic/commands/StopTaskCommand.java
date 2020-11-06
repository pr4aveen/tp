package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

/**
 * Stops a previously started timer tracking a task in Momentum.
 */
public class StopTaskCommand extends StopCommand {

    private final Project parentProject;

    /**
     * Creates a StopTaskCommand that stops the timer for the task.
     *
     * @param targetIndex The index of the task to stop.
     * @param parentProject The parent Project of the task.
     */
    public StopTaskCommand(Index targetIndex, Project parentProject) {
        super(targetIndex);
        requireNonNull(parentProject);
        this.parentProject = parentProject;
    }

    /**
     * Stops the timer for the task in the provided model.
     *
     * @param model {@code Model} containing the task whose timer to stop.
     * @return feedback message of timer result, for display.
     * @throws CommandException If an error occurs when stopping the timer, or if a timer is already running.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<TrackedItem> lastShownList = model.getDisplayList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToStop = lastShownList.get(targetIndex.getZeroBased());

        if (!trackedItemToStop.isRunning()) {
            throw new CommandException(MESSAGE_NO_TIMER_ERROR);
        }

        TrackedItem newTrackedItem = trackedItemToStop.stopTimer();
        String timePassed =
                StringUtil.formatMinutesToString(newTrackedItem.getTimer().getTimeBetween(ChronoUnit.MINUTES));

        Project newProject = parentProject.setTask(trackedItemToStop, newTrackedItem);
        model.setTrackedItem(parentProject, newProject);

        model.commitToHistory();

        return new CommandResult(String.format(MESSAGE_STOP_TIMER_SUCCESS, targetIndex.getOneBased(),
                timePassed));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StopTaskCommand // instanceof handles nulls
                && targetIndex.equals(((StopTaskCommand) other).targetIndex))
                && parentProject.equals(((StopTaskCommand) other).parentProject); // state check
    }
}
