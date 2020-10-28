package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.model.Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS;

import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;

/**
 * Lists all projects in the project book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS_PROJECTS = "Listed all projects";

    public static final String MESSAGE_SUCCESS_TASKS = "Listed all tasks belonging to %s";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_TRACKED_ITEMS);
        model.setIsPreviousCommandTimerToFalse();
        model.commitToHistory();
        if (model.getViewMode() == ViewMode.PROJECTS) {
            return new CommandResult(MESSAGE_SUCCESS_PROJECTS);
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS_TASKS,
                    model.getCurrentProject().getName().fullName));
        }
    }
}
