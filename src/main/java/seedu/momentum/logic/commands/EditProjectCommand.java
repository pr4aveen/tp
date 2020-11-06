//@@author pr4aveen

package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.TrackedItem;

/**
 * Edits a project in Momentum.
 */
public class EditProjectCommand extends EditCommand {

    /**
     * Create an EditProjectCommand that edits a project.
     *
     * @param index                     Of the project in the model to edit.
     * @param editTrackedItemDescriptor Details to edit the project with.
     */
    public EditProjectCommand(Index index, EditTrackedItemDescriptor editTrackedItemDescriptor) {
        super(index, editTrackedItemDescriptor);
    }

    /**
     * Edits a project in the provided model.
     *
     * @param model {@code Model} containing the project to edit.
     * @return Feedback message of editing result, for display.
     * @throws CommandException If an error occurs during editing process.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<TrackedItem> lastShownList = model.getDisplayList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        TrackedItem trackedItemToEdit = lastShownList.get(index.getZeroBased());
        TrackedItem editedTrackedItem = createEditedTrackedItem(trackedItemToEdit, editTrackedItemDescriptor, model);

        if (!trackedItemToEdit.isSameAs(editedTrackedItem) && model.hasTrackedItem(editedTrackedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        try {
            model.setTrackedItem(trackedItemToEdit, editedTrackedItem);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
        }

        model.rescheduleReminders();
        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_EDIT_PROJECT_SUCCESS, editedTrackedItem));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditProjectCommand)) {
            return false;
        }

        // state check
        EditProjectCommand e = (EditProjectCommand) other;
        return index.equals(e.index)
                && editTrackedItemDescriptor.equals(e.editTrackedItemDescriptor);
    }
}
