package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.temporal.ChronoUnit;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.work_duration.WorkDuration;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class StopCommand extends Command {

    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Stops the project identified by the index number used in the displayed project list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_STOP_TIMER_SUCCESS = "Stopped Project: %1$s! Total Duration: %1$s";
    public static final String MESSAGE_NO_TIMER_ERROR = "There is no timer running for this project.";

    private final Index targetIndex;

    public StopCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Project> lastShownList = model.getFilteredProjectList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToStop = lastShownList.get(targetIndex.getZeroBased());

        if (!model.hasActiveTimer()) {
            throw new CommandException(MESSAGE_NO_TIMER_ERROR);
        }

        WorkDuration duration = model.stopTimer(projectToStop);

        return new CommandResult(String.format(MESSAGE_STOP_TIMER_SUCCESS, targetIndex.getOneBased(),
                duration.getTimeBetween(ChronoUnit.MINUTES)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StopCommand // instanceof handles nulls
                && targetIndex.equals(((StopCommand) other).targetIndex)); // state check
    }
}
