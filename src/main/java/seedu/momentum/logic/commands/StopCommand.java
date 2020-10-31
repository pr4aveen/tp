package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

/**
 * Stops a previously started timerWrapper tracking a project identified using it's displayed index.
 */
public class StopCommand extends Command {

    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + "INDEX";

    public static final String MESSAGE_STOP_TIMER_SUCCESS = "Stopped %s. Total Duration: %s";
    public static final String MESSAGE_NO_TIMER_ERROR = "There is no timer running for this project.";

    private final Index targetIndex;
    private final Project parentProject;

    /**
     * Creates a StopCommand that stops the timerWrapper for a project.
     *
     * @param targetIndex The project to stop.
     */
    public StopCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        parentProject = null;
    }

    /**
     * Creates a StartCommand that stops the timerWrapper for a task.
     *
     * @param targetIndex The task to stop.
     * @param parentProject The parent Project of the task.
     */
    public StopCommand(Index targetIndex, Project parentProject) {
        this.targetIndex = targetIndex;
        this.parentProject = parentProject;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<TrackedItem> lastShownList = model.getDisplayList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToStop = lastShownList.get(targetIndex.getZeroBased());

        if (!trackedItemToStop.isRunning()) {
            throw new CommandException(MESSAGE_NO_TIMER_ERROR);
        }

        TrackedItem newTrackedItem = trackedItemToStop.stopTimer();

        if (model.getViewMode() == ViewMode.PROJECTS) {
            model.setTrackedItem(trackedItemToStop, newTrackedItem);
        } else {
            assert parentProject != null;
            Project newProject = parentProject.setTask(trackedItemToStop, newTrackedItem);
            model.setTrackedItem(parentProject, newProject);
        }

        model.rescheduleReminders();
        model.commitToHistory();

        return new CommandResult(String.format(MESSAGE_STOP_TIMER_SUCCESS, targetIndex.getOneBased(),
                newTrackedItem.getTimer().getTimeBetween(ChronoUnit.MINUTES)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StopCommand // instanceof handles nulls
                && targetIndex.equals(((StopCommand) other).targetIndex)); // state check
    }
}
