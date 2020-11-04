package seedu.momentum.logic.commands;

import seedu.momentum.model.Model;

/**
 * Terminates Momentum.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Project Book as requested ...";

    /**
     * Exits the application.
     *
     * @param model Not important. Only exists to match the signature of the abstract class.
     * @return feedback message of exiting the application, for display.
     */
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
