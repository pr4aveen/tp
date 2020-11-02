package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

/**
 * Starts a timerWrapper tracking a project identified using it's displayed index.
 */
public abstract class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + "INDEX";

    public static final String MESSAGE_START_TIMER_SUCCESS = "Started %1$s, at: ";
    public static final String MESSAGE_EXISTING_TIMER_ERROR = "There is already a timer running for this project";

    protected final Index targetIndex;

    /**
     * Creates a StartCommand that starts the timerWrapper for a project.
     *
     * @param targetIndex The project to start.
     */
    public StartCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartProjectCommand // instanceof handles nulls
                && targetIndex.equals(((StartProjectCommand) other).targetIndex)); // state check
    }
}
