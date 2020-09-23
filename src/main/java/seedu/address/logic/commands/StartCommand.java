package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.work_duration.DurationUtil;
import seedu.address.model.work_duration.WorkDuration;

/**
 * Deletes a person identified using it's displayed index from the address book.
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

    public StartCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Project> lastShownList = model.getFilteredProjectList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToStart = lastShownList.get(targetIndex.getZeroBased());

        if (model.hasActiveTimer()) {
            throw new CommandException(MESSAGE_EXISTING_TIMER_ERROR);
        }

        WorkDuration duration = model.startTimer(projectToStart);

        return new CommandResult(String.format(MESSAGE_START_TIMER_SUCCESS, targetIndex.getOneBased()) +
                duration.getStartTime().format(DurationUtil.dateTimeFormatter));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartCommand // instanceof handles nulls
                && targetIndex.equals(((StartCommand) other).targetIndex)); // state check
    }
}
