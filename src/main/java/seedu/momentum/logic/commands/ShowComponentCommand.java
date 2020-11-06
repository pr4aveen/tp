//@@author claracheong4

package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.logic.parser.ShowComponentCommandParser;
import seedu.momentum.model.Model;

/**
 * Toggles the visibility of a UI component.
 */
public class ShowComponentCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + PREFIX_TAG + " ] ";

    public static final String MESSAGE_SUCCESS = "%s is %s from the sidebar.\n";

    public static final String REMOVED = "removed";

    public static final String SHOWN = "shown";

    private final ShowComponentCommandParser.ComponentType componentType;

    /**
     * Creates a ShowComponentCommand which shows or hide a UI component.
     *
     * @param componentType The component types to show or hide.
     */
    public ShowComponentCommand(ShowComponentCommandParser.ComponentType componentType) {
        requireNonNull(componentType);

        this.componentType = componentType;
    }

    /**
     * Toggle the visibility of the UI components.
     *
     * @param model {@code Model} to perform the changes.
     * @return Feedback message of update result, for display.
     * @throws CommandException If an error occurs during UI updating process.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert ShowComponentCommandParser.ComponentType.TAGS == this.componentType;
        requireNonNull(model);

        boolean isShown = model.getIsTagsVisible().get();
        model.showOrHideTags();
        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, componentType.toString(), isShown ? REMOVED : SHOWN));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowComponentCommand // instanceof handles nulls
                && componentType.equals(((ShowComponentCommand) other).componentType)); // state check
    }
}
