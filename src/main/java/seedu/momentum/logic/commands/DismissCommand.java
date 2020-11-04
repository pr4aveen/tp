package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

/**
 * Shows or hides a component.
 */
public class DismissCommand extends Command {

    public static final String COMMAND_WORD = "dismiss";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Reminder dismissed.";

    public static final String MESSAGE_FAILURE = "No reminder to dismiss.";

    /**
     * Instantiates a new DismissCommand which dismisses reminders.
     */
    public DismissCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isReminderEmpty().getValue()) {
            model.removeReminder();
            model.commitToHistory();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof DismissCommand; // instanceof handles nulls
    }
}
