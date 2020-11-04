package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.core.Messages.MESSAGE_TEXT_PROJECT;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;

/**
 * Adds a project to Momentum.
 */
public class AddProjectCommand extends AddCommand {

    /**
     * Creates an AddCommand to add the specified {@code Project}
     *
     * @param project The project to add.
     */
    public AddProjectCommand(Project project) {
        super(project);
    }

    /**
     * Adds a project to the provided model.
     *
     * @param model {@code Model} which the command will add the project to.
     * @return feedback message of the result of adding, for display.
     * @throws CommandException If an error occurs when adding the project.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTrackedItem(project)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ENTRY, MESSAGE_TEXT_PROJECT));
        }

        model.addTrackedItem(project);
        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_TEXT_PROJECT, project));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddProjectCommand // instanceof handles nulls
                && project.equals(((AddProjectCommand) other).project));
    }
}
