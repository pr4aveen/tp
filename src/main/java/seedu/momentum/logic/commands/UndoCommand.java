//@@author kkangs0226

package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

/**
 * Undoes the previously executed command in Momentum.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undid previous command";

    public static final String MESSAGE_CANNOT_UNDO = "You have reached the end of your history.\n"
            + "There are no commands to undo.";

    /**
     * Undoes the changes made to the provided model, if possible.
     *
     * @param model {@code Model} containing the data which the previous command had operated on.
     * @return Feedback message of the redo result, for display.
     * @throws CommandException If an error occurs during redo process, or if undoing is not possible.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.canUndoCommand()) {
            model.undoCommand();
            return new CommandResult(MESSAGE_SUCCESS);
        }
        throw new CommandException(MESSAGE_CANNOT_UNDO);
    }
}
