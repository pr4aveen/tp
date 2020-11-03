package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ProjectBook;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.Project;

/**
 * Represents a Clear command in Momentum.
 * A Clear command can remove different data depending on the state of the application. The should be specified
 * and implemented as extensions to this class.
 */
public abstract class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS_TASK = "All tasks have been cleared!";
    public static final String MESSAGE_SUCCESS_ALL = "All projects has been cleared!";

    /**
     * Removes some data from the provided model.
     *
     * @param model {@code Model} to remove data from.
     * @return feedback message of result of removing data for display.
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model);
}
