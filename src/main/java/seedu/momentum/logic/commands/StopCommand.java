package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

/**
 * Stops a previously started timerWrapper tracking a project identified using it's displayed index.
 */
public abstract class StopCommand extends Command {

    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + "INDEX";

    public static final String MESSAGE_STOP_TIMER_SUCCESS = "Stopped %s. Total Duration: %s";
    public static final String MESSAGE_NO_TIMER_ERROR = "There is no timer running for this.";

    protected final Index targetIndex;


    /**
     * Creates a StopCommand that stops the timerWrapper for a project.
     *
     * @param targetIndex The project to stop.
     */
    public StopCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StopCommand // instanceof handles nulls
                && targetIndex.equals(((StopCommand) other).targetIndex)); // state check
    }
}
