package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.TrackedItem;

/**
 * Adds a project to the project book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_COMPLETION_STATUS + "] "
            + String.format("[%sDEADLINE_DATE [%sDEADLINE_TIME] ] ", PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME)
            + "[" + PREFIX_REMINDER + "REMINDER_DATE_AND_TIME] "
            + "[" + PREFIX_TAG + "TAG]...";

    public static final String MESSAGE_SUCCESS = "New %1$s added: %2$s";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the project book";
    public static final String TEXT_PROJECT = "Project";
    public static final String TEXT_TASK = "Task";

    private TrackedItem toAdd;

    private Project projectToAddTask;
    private Task taskToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Project}
     */
    public AddCommand(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        toAdd = trackedItem;
    }

    /**
     * Creates an AddCommand to add the specified {@code Task} to the specified {@code Project}
     */
    public AddCommand(Task task, Project project) {
        requireAllNonNull(task, project);
        taskToAdd = task;
        projectToAddTask = project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ViewMode viewMode = model.getViewMode();

        if (viewMode == ViewMode.PROJECTS) {
            if (model.hasTrackedItem(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
            }

            model.addTrackedItem(toAdd);
            model.commitToHistory();
            return new CommandResult(String.format(MESSAGE_SUCCESS, TEXT_PROJECT, toAdd));
        } else {
            if (projectToAddTask.hasTask(taskToAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
            }
            Project projectBeforeAdd = projectToAddTask;
            Project projectAfterAdd = projectToAddTask.addTask(taskToAdd);
            model.setTrackedItem(projectBeforeAdd, projectAfterAdd);
            model.rescheduleReminders();
            model.viewTasks(projectAfterAdd);
            model.commitToHistory();
            return new CommandResult(String.format(MESSAGE_SUCCESS, TEXT_TASK, taskToAdd));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
