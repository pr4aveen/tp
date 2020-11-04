package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

/**
 * Represents a command that starts a timer tracking an item in Momentum.
 */
public abstract class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + "INDEX";

    public static final String MESSAGE_START_TIMER_SUCCESS = "Started %1$s, at: ";
    public static final String MESSAGE_EXISTING_TIMER_ERROR = "There is already a timer running for this";

    protected final Index targetIndex;

    /**
     * Creates a StartCommand that starts the timer for the item at the specified index.
     *
     * @param targetIndex The index of the item to start.
     */
    public StartCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Starts the timer for the item in the provided model.
     *
     * @param model {@code Model} containing the item whose timer to start.
     * @return feedback message of timer result, for display.
     * @throws CommandException If an error occurs when starting the timer.
     */
    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
