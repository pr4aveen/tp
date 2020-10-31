package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.TrackedItem;

public class StopProjectCommand extends StopCommand {

    /**
     * Creates a StopProjectCommand that stops the timerWrapper for a project.
     *
     * @param targetIndex The project to stop.
     */
    public StopProjectCommand(Index targetIndex) {
        super(targetIndex);
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

        model.setTrackedItem(trackedItemToStop, newTrackedItem);
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
