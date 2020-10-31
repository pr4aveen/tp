package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.model.Model;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.Project;

/**
 * Clears the project book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS_TASK = "All tasks have been cleared!";
    public static final String MESSAGE_SUCCESS_ALL = "All projects has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.getViewMode() == ViewMode.PROJECTS) {
            model.setVersionedProjectBook(new ProjectBook());
            model.commitToHistory();
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        } else {
            Project projectBeforeClear = model.getCurrentProject();
            Project projectAfterClear = model.getCurrentProject().clearTasks();
            model.setTrackedItem(projectBeforeClear, projectAfterClear);
            model.viewTasks(projectAfterClear);
            model.commitToHistory();
            return new CommandResult(MESSAGE_SUCCESS_TASK);
        }
    }
}
