package seedu.momentum.logic.commands;

import seedu.momentum.model.Model;

/**
 * View all projects in Momentum.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Showing all projects...";

    /**
     * Updates the model to show all projects.
     *
     * @param model {@code Model} containing the projects.
     * @return feedback message of the result, for display.
     */
    @Override
    public CommandResult execute(Model model) {
        model.viewProjects();
        model.commitToHistory();
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false);
    }
}
