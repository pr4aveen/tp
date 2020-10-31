package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.TrackedItem;

public class StartProjectCommand extends StartCommand {

    /**
     * Creates a StartCommand that starts the timerWrapper for a project.
     *
     * @param targetIndex The project to start.
     */
    public StartProjectCommand(Index targetIndex) {
        super(targetIndex);
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
        model.setTrackedItem(trackedItemToStart, newTrackedItem);

        model.rescheduleReminders();
        model.commitToHistory();

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
