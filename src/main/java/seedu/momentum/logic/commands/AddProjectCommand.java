package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.Project;

public class AddProjectCommand extends AddCommand {

    public AddProjectCommand(Project project) {
        super(project);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTrackedItem(project)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ENTRY, TEXT_PROJECT));
        }

        model.addTrackedItem(project);
        model.commitToHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, TEXT_PROJECT, project));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddProjectCommand // instanceof handles nulls
                && project.equals(((AddProjectCommand) other).project));
    }
}
