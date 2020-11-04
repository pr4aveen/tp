package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

/**
 * Represents a command that stops a previously started timer tracking an item in Momentum.
 */
public abstract class StopCommand extends Command {

    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + "INDEX";

    public static final String MESSAGE_STOP_TIMER_SUCCESS = "Stopped %s. Total Duration: %s";
    public static final String MESSAGE_NO_TIMER_ERROR = "There is no timer running for this.";

    protected final Index targetIndex;


    /**
     * Creates a StopCommand that stops the timer for the item.
     *
     * @param targetIndex The index of the item to stop.
     */
    public StopCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Stops the timer for the item in the provided model.
     *
     * @param model {@code Model} containing the item whose timer to stop.
     * @return feedback message of timer result, for display.
     * @throws CommandException If an error occurs when stopping the timer.
     */
    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StopCommand // instanceof handles nulls
                && targetIndex.equals(((StopCommand) other).targetIndex)); // state check
    }
}
