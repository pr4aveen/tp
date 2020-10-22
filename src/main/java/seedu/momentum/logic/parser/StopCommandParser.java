package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.StopCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;

/**
 * Parses input arguments and creates a new StopCommand object
 */
public class StopCommandParser implements Parser<StopCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StopCommand
     * and returns a StopCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public StopCommand parse(String args, Model model) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new StopCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StopCommand.MESSAGE_USAGE), pe);
        }
    }

}
