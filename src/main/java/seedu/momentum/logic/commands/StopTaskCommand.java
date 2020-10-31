package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

public class StopTaskCommand extends StopCommand {

    private final Project parentProject;

    /**
     * Creates a StopCommand that stops the timerWrapper for a task.
     *
     * @param targetIndex The task to stop.
     * @param parentProject The parent Project of the task.
     */
    public StopTaskCommand(Index targetIndex, Project parentProject) {
        super(targetIndex);
        requireNonNull(parentProject);
        this.parentProject = parentProject;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<TrackedItem> lastShownList = model.getFilteredTrackedItemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToStop = lastShownList.get(targetIndex.getZeroBased());

        if (!trackedItemToStop.isRunning()) {
            throw new CommandException(MESSAGE_NO_TIMER_ERROR);
        }

        TrackedItem newTrackedItem = trackedItemToStop.stopTimer();

        Project newProject = parentProject.setTask(trackedItemToStop, newTrackedItem);
        model.setTrackedItem(parentProject, newProject);

        model.rescheduleReminders();
        model.commitToHistory();

        return new CommandResult(String.format(MESSAGE_STOP_TIMER_SUCCESS, targetIndex.getOneBased(),
                newTrackedItem.getTimer().getTimeBetween(ChronoUnit.MINUTES)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StopTaskCommand // instanceof handles nulls
                && targetIndex.equals(((StopTaskCommand) other).targetIndex))
                && parentProject.equals(((StopTaskCommand) other).parentProject); // state check
    }
}
