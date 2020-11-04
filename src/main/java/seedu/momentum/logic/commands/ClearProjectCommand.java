package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.model.Model;
import seedu.momentum.model.ProjectBook;

/**
 * Clears all projects stored in Momentum.
 */
public class ClearProjectCommand extends ClearCommand {
    /**
     * Removes all projects in the provided model.
     * This only happens when the user is viewing all projects.
     *
     * @param model {@code Model} to remove projects from.
     * @return feedback message of the operation result, for display.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setVersionedProjectBook(new ProjectBook());
        model.commitToHistory();
        return new CommandResult(MESSAGE_SUCCESS_ALL);
    }
}
