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
 * View all of a project's tasks in Momentum.
 */
public class ProjectViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + "INDEX";

    public static final String MESSAGE_VIEW_PROJECT_SUCCESS = "Viewing Project: %1$s";

    public static final String MESSAGE_NOT_PROJECT = "You cannot view tasks within a project!";

    private final Index targetIndex;

    /**
     * Creates a ProjectViewCommand that displays all the tasks belonging to the project with the specified index.
     *
     * @param targetIndex Index of the projects whose tasks are to be displayed.
     */
    public ProjectViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Updates teh model to display all tasks belonging to the project.
     *
     * @param model {@code Model} containing the project.
     * @return feedback message of displaying result, for display.
     * @throws CommandException If an error occurs during update process.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<TrackedItem> lastShownList = model.getDisplayList();

        if (model.getViewMode() != ViewMode.PROJECTS) {
            throw new CommandException(MESSAGE_NOT_PROJECT);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToView = (Project) lastShownList.get(targetIndex.getZeroBased());
        model.resetView();
        model.viewTasks(projectToView);
        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_VIEW_PROJECT_SUCCESS, projectToView.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectViewCommand // instanceof handles nulls
                && targetIndex.equals(((ProjectViewCommand) other).targetIndex)); // state check
    }
}
