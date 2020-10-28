package seedu.momentum.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.momentum.model.Model;
import seedu.momentum.model.ProjectBook;

/**
 * Clears the project book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Momentum has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setVersionedProjectBook(new ProjectBook());
        model.setIsPreviousCommandTimerToFalse();
        model.commitToHistory();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
