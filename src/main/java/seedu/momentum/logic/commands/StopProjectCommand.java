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
 * Stops a previously started timer tracking a project in Momentum.
 */
public class StopProjectCommand extends StopCommand {

    /**
     * Creates a StopProjectCommand that stops the timer for a project.
     *
     * @param targetIndex The index of the project to stop.
     */
    public StopProjectCommand(Index targetIndex) {
        super(targetIndex);
    }

    /**
     * Stops the timer for the project in the provided model.
     *
     * @param model {@code Model} containing the item whose timer to stop.
     * @return feedback message of timer result, for display.
     * @throws CommandException If an error occurs when stopping the timer, or if a timer is already running.
     */
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

        model.setTrackedItem(trackedItemToStop, newTrackedItem);
        model.rescheduleReminders();
        model.commitToHistory();

        return new CommandResult(String.format(MESSAGE_STOP_TIMER_SUCCESS, targetIndex.getOneBased(),
                newTrackedItem.getTimer().getTimeBetween(ChronoUnit.MINUTES)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StopProjectCommand // instanceof handles nulls
                && targetIndex.equals(((StopProjectCommand) other).targetIndex)); // state check
    }
}
