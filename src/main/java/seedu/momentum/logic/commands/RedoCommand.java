package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redid previously undone command";

    public static final String MESSAGE_CANNOT_REDO = "You have reached the end of your history.\n"
            + "There are no commands to redo.";

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
