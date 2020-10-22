package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.TrackedItem;

/**
 * Stops a previously started timer tracking a project identified using it's displayed index.
 */
public class StopCommand extends Command {

    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Stops the project identified by the index number used in the displayed project list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_STOP_TIMER_SUCCESS = "Stopped Project: %s! Total Duration: %s";
    public static final String MESSAGE_NO_TIMER_ERROR = "There is no timer running for this project.";

    private final Index targetIndex;

    public StopCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
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
        model.setTrackedItem(trackedItemToStop, newTrackedItem);
        model.removeRunningTimer(trackedItemToStop);

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
