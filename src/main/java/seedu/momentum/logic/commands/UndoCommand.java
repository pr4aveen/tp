package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undid previous command";

    public static final String MESSAGE_CANNOT_UNDO = "You have reached the end of your history.\n"
            + "There are no commands to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoCommand()) {
            throw new CommandException(MESSAGE_CANNOT_UNDO);
        } else {
            model.undoCommand();
            return new CommandResult(MESSAGE_SUCCESS);
        }

    }
}
