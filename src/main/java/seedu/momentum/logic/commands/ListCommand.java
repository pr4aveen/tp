//@@author

package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.model.Model.PREDICATE_SHOW_ALL_TRACKED_ITEMS;

import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;

/**
 * List all items available in the current context in Momentum.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS_PROJECTS = "Listed all projects";

    public static final String MESSAGE_SUCCESS_TASKS = "Listed all tasks belonging to %s";

    /**
     * Lists all items.
     * All projects are listed if the model is in project view.
     * All tasks of a project are listed if the model is in task view.
     *
     * @param model {@code Model} containing the items.
     * @return Feedback message of the result, for display.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updatePredicate(PREDICATE_SHOW_ALL_TRACKED_ITEMS);
        model.commitToHistory();
        if (model.getViewMode() == ViewMode.PROJECTS) {
            return new CommandResult(MESSAGE_SUCCESS_PROJECTS);
        }
        String projectName = model.getCurrentProject().getName().fullName;
        return new CommandResult(String.format(MESSAGE_SUCCESS_TASKS, projectName));
    }
}
