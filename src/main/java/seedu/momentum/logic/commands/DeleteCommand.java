//@@author pr4aveen

package seedu.momentum.logic.commands;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;

/**
 * Represents a Delete command in Momentum.
 * Commands that delete different items should extend this class with an implementation specific to the item
 * being deleted.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + "INDEX";
    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Deleted Project: %1$s";
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    protected final Index targetIndex;

    /**
     * Creates a DeleteCommand that deletes an item at a given index from the model.
     *
     * @param targetIndex Index of the item to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Deletes the item from the provided model.
     *
     * @param model {@code Model} from which to delete the item.
     * @return Feedback message of the result of deleting the item, for display.
     * @throws CommandException If an error occurs during the deletion.
     */
    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

}
