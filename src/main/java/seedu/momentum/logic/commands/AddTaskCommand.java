//@@author pr4aveen

package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;

/**
 * Adds a task to a project in Momentum.
 */
public class AddTaskCommand extends AddCommand {

    public static final String TEXT_TASK = "Task";
    private final Task taskToAdd;

    /**
     * Creates a new AddTaskCommand to add a task to a specified project.
     *
     * @param task To be added to the project.
     * @param project To add the task to.
     */
    public AddTaskCommand(Task task, Project project) {
        super(project);
        requireNonNull(task);
        this.taskToAdd = task;
    }

    /**
     * Adds a task to the project in the provided model.
     *
     * @param model {@code Model} containing the project which the command will add the task to.
     * @return Feedback message of the result of adding for display.
     * @throws CommandException If an error occurs when adding the task.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (project.hasTask(taskToAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ENTRY, TEXT_TASK));
        }
        Project projectBeforeAdd = project;
        Project projectAfterAdd = project.addTask(taskToAdd);
        model.setTrackedItem(projectBeforeAdd, projectAfterAdd);
        model.viewTasks(projectAfterAdd);
        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, TEXT_TASK, taskToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && project.equals(((AddTaskCommand) other).project)
                && taskToAdd.equals(((AddTaskCommand) other).taskToAdd));
    }
}
