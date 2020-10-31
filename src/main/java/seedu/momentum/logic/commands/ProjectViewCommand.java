package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;

/**
 * View all of a project's tasks, identified using it's displayed index from the project book.
 */
public class ProjectViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views all of a project's tasks, identified by the index number used in the displayed project list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PROJECT_SUCCESS = "Viewing Project: %1$s";

    private final Index targetIndex;

    public ProjectViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<TrackedItem> lastShownList = model.getFilteredTrackedItemList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        if (model.getViewMode() != ViewMode.PROJECTS) {
            throw new CommandException(Messages.MESSAGE_NOT_PROJECT);
        }

        Project projectToView = (Project) lastShownList.get(targetIndex.getZeroBased());
        model.viewTasks(projectToView);
        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, projectToView.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectViewCommand // instanceof handles nulls
                && targetIndex.equals(((ProjectViewCommand) other).targetIndex)); // state check
    }
}
