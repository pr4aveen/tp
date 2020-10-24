package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
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

    public static final String COMMAND_WORD = "project";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a project to the project book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + String.format("[%sDEADLINE_DATE [%sDEADLINE_TIME] ] ", PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME)
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DESCRIPTION + "random project"
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New project added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the project book";

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
     * Creates an AddCommand to add the specified {@code Task} to the specificed {@code Project}
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
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } else {
            if (projectToAddTask.hasTask(taskToAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_PROJECT);
            }
            projectToAddTask.addTask(taskToAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskToAdd));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
