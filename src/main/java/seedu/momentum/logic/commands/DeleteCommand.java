package seedu.momentum.logic.commands;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

/**
 * Deletes a project identified using it's displayed index from the project book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + "INDEX";
    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Deleted Project: %1$s";
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    protected final Index targetIndex;

    /**
     * Deletes a project at a given index from the project book.
     *
     * @param targetIndex index of the project to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

}
