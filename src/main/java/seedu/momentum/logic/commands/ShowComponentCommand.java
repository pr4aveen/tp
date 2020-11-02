package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;

import java.util.List;

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

    public static final String MESSAGE_SUCCESS = "%s is %s from the sidebar.\n";

    public static final String MESSAGE_FAILURE = "No component to show or hide.";

    public static final String REMOVED = "removed";

    public static final String SHOWN = "shown";

    private final List<ShowComponentCommandParser.ComponentType> componentTypes;

    /**
     * Instantiates a new Show component command which shows or hide a command.
     *
     * @param componentTypes the component types.
     */
    public ShowComponentCommand(List<ShowComponentCommandParser.ComponentType> componentTypes) {
        requireNonNull(componentTypes);
        this.componentTypes = componentTypes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String resultString = "";
        for (ShowComponentCommandParser.ComponentType componentType : componentTypes) {
            switch (componentType) {
            case REMINDER:
                if (!model.isReminderEmpty().getValue()) {
                    model.removeReminder();
                    model.commitToHistory();
                    resultString += String.format(MESSAGE_SUCCESS, componentType.toString(), REMOVED);
                }
                break;
            case TAGS:
                boolean isShown = model.getIsTagsVisible().get();
                model.showOrHideTags();
                resultString += String.format(MESSAGE_SUCCESS, componentType.toString(), isShown ? REMOVED : SHOWN);
                break;
            default:
                throw new CommandException(MESSAGE_FAILURE);
            }
        }
        
        if (resultString.isBlank()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(resultString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowComponentCommand // instanceof handles nulls
                && componentTypes.equals(((ShowComponentCommand) other).componentTypes)); // state check
    }
}
