package seedu.momentum.logic.commands;

import seedu.momentum.model.Model;

/**
 * Displays help instructions for every command.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    /**
     * Displays the help instructions.
     *
     * @param model Not important. Only exists to match the signature of the abstract class.
     * @return feedback message of editing result, for display.
     */
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
