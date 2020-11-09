//@@author pr4aveen

package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;

/**
 * Clears all tasks belonging to a project in Momentum.
 */
public class ClearTaskCommand extends ClearCommand {
    /**
     * Removes all tasks in the currently shown project of the provided model.
     * This only happens when the user is viewing a specific project's tasks.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result, for display.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Project projectBeforeClear = model.getCurrentProject();
        Project projectAfterClear = model.getCurrentProject().clearTasks();
        model.setTrackedItem(projectBeforeClear, projectAfterClear);
        model.viewTasks(projectAfterClear);
        model.commitToHistory();
        return new CommandResult(MESSAGE_SUCCESS_TASK);
    }
}
