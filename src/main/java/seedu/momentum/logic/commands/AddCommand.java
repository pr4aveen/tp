package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;

/**
 * Represents a add command in Momentum.
 * Commands that add different items should extend this class with an implementation specific to the item
 * being added.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_COMPLETION_STATUS + "] "
            + String.format("[%sDEADLINE_DATE [%sDEADLINE_TIME] ] ", PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME)
            + "[" + PREFIX_REMINDER + "REMINDER_DATE_AND_TIME] "
            + "[" + PREFIX_TAG + "TAG]...";

    public static final String MESSAGE_SUCCESS = "New %1$s added: %2$s";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This %s already exists in the project book";

    protected Project project;

    /**
     * Creates an AddCommand to add the specified {@code Project}
     *
     * @param project The project to add.
     */
    public AddCommand(Project project) {
        requireNonNull(project);
        this.project = project;
    }

    /**
     * Adds an item to the provided model.
     *
     * @param model {@code Model} which the command will add the item to.
     * @return feedback message of the result of adding, for display
     * @throws CommandException If an error occurs when adding the item.
     */
    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
