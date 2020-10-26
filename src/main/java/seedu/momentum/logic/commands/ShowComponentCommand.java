package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;

import seedu.momentum.logic.parser.ShowComponentCommandParser;
import seedu.momentum.model.Model;

public class ShowComponentCommand extends Command {
    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows or hide the component of the sidebar. "
            + "Parameters: "
            + PREFIX_REMINDER + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER;

    public static final String MESSAGE_SUCCESS = "%s is %s from the sidebar.";

    private static final String REMOVED = "removed";
    private static final String SHOWN = "shown";

    private final ShowComponentCommandParser.ComponentType componentType;

    public ShowComponentCommand(ShowComponentCommandParser.ComponentType componentType) {
        this.componentType = componentType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        switch (componentType) {
        case REMINDER:
            model.removeReminder();
            return new CommandResult(String.format(MESSAGE_SUCCESS, componentType.toString(), REMOVED));
        default:
            // never reach here
            return new CommandResult("");
        }
    }
}
