package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.TrackedItem;

/**
 * Deletes projects in Momentum.
 */
public class DeleteProjectCommand extends DeleteCommand {
    /**
     * Creates a {@code DeleteProjectCommand} that deletes a project at the given index form the model.
     *
     * @param targetIndex index of the project to be deleted.
     */
    public DeleteProjectCommand(Index targetIndex) {
        super(targetIndex);
    }

    /**
     * Deletes the project from the provided model.
     *
     * @param model {@code Model} from which to delete the project.
     * @return feedback message of the result of deleting the project, for display.
     * @throws CommandException If an error occurs during the deletion.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<TrackedItem> lastShownList = model.getDisplayList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.deleteTrackedItem(trackedItemToDelete);
        model.rescheduleReminders();
        model.commitToHistory();

        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, trackedItemToDelete));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteProjectCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteProjectCommand) other).targetIndex)); // state check
    }
}
