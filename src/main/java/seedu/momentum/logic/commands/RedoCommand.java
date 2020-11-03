package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

/**
 * Redoes a change caused by the latest undo command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redid previously undone command";

    public static final String MESSAGE_CANNOT_REDO = "You have reached the end of your history.\n"
            + "There are no commands to redo.";

    /**
     * Redo the changes made to the provided model, if possible.
     *
     * @param model {@code Model} containing the data which the undo command had operated on.
     * @return feedback message of the redo result, for display
     * @throws CommandException If an error occurs during redo process, or if redoing is not possible.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoCommand()) {
            throw new CommandException(MESSAGE_CANNOT_REDO);
        } else {
            model.redoCommand();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

}
