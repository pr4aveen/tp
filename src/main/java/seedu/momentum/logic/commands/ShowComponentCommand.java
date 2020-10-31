package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.ShowComponentCommandParser;
import seedu.momentum.model.Model;

/**
 * Shows or hides a component.
 */
public class ShowComponentCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + PREFIX_REMINDER + " ] ";

    public static final String MESSAGE_SUCCESS = "%s is %s from the sidebar.";

    public static final String MESSAGE_FAILURE = "No component to show or hide.";

    public static final String REMOVED = "removed";

    public static final String SHOWN = "shown";

    private final ShowComponentCommandParser.ComponentType componentType;

    /**
     * Instantiates a new Show component command which shows or hide a command.
     *
     * @param componentType the component type.
     */
    public ShowComponentCommand(ShowComponentCommandParser.ComponentType componentType) {
        requireNonNull(componentType);
        this.componentType = componentType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        switch (componentType) {
        case REMINDER:
            if (!model.isReminderEmpty().getValue()) {
                model.removeReminder();
                model.commitToHistory();
                return new CommandResult(String.format(MESSAGE_SUCCESS, componentType.toString(), REMOVED));
            }
            break;
        default:
            throw new CommandException(MESSAGE_FAILURE);
        }
        throw new CommandException(MESSAGE_FAILURE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowComponentCommand // instanceof handles nulls
                && componentType.equals(((ShowComponentCommand) other).componentType)); // state check
    }
}
