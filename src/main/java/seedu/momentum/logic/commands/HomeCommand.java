package seedu.momentum.logic.commands;

import seedu.momentum.model.Model;

/**
 * View all projects in the program.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Showing all projects...";

    @Override
    public CommandResult execute(Model model) {
        model.viewProjects();
        System.out.println("Executing Home Command");
        model.commitToHistory();
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false);
    }

}
