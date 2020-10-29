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
 * Starts a timerWrapper tracking a project identified using it's displayed index.
 */
public class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts the project identified by the index number used in the displayed project list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_START_TIMER_SUCCESS = "Started Project %1$s, at: ";
    public static final String MESSAGE_EXISTING_TIMER_ERROR = "There is already a timer running for this project";

    private final Index targetIndex;
    private final Project parentProject;

    /**
     * Creates a StartCommand that starts the timerWrapper for a project.
     *
     * @param targetIndex The project to start.
     */
    public StartCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.parentProject = null;
    }

    /**
     * Creates a StartCommand that starts the timerWrapper for a task.
     *
     * @param targetIndex The task to start.
     * @param parentProject The parent project of the task.
     */
    public StartCommand(Index targetIndex, Project parentProject) {
        this.targetIndex = targetIndex;
        this.parentProject = parentProject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<TrackedItem> lastShownList = model.getFilteredTrackedItemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToStart = lastShownList.get(targetIndex.getZeroBased());

        if (trackedItemToStart.isRunning()) {
            throw new CommandException(MESSAGE_EXISTING_TIMER_ERROR);
        }

        TrackedItem newTrackedItem = trackedItemToStart.startTimer();
        if (model.getViewMode() == ViewMode.PROJECTS) {
            model.setTrackedItem(ViewMode.PROJECTS, trackedItemToStart, newTrackedItem);
        } else {
            assert parentProject != null;
            parentProject.setTask(trackedItemToStart, newTrackedItem);
        }

        model.rescheduleReminders();
        model.commitToHistory(true);

        return new CommandResult(String.format(MESSAGE_START_TIMER_SUCCESS, targetIndex.getOneBased())
                + newTrackedItem.getTimer().getStartTime().getFormatted());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartCommand // instanceof handles nulls
                && targetIndex.equals(((StartCommand) other).targetIndex)); // state check
    }
}
