package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.model.Model;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ViewMode;

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
            model.setProjectBook(new ProjectBook());
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        } else {
            model.getCurrentProject().clearTasks();
            model.viewTasks(model.getCurrentProject());
            return new CommandResult(MESSAGE_SUCCESS_TASK);
        }
    }
}
