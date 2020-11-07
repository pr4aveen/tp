//@@author

package seedu.momentum.logic.parser;

import seedu.momentum.logic.commands.Command;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput Arguments from the user.
     * @param model     The current model, to provide context for parsing the arguments.
     * @return The relevant command.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput, Model model) throws ParseException;
}
