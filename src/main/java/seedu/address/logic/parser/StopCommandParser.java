package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.StartCommand;
import seedu.address.logic.commands.StopCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new StartCommand object
 */
public class StopCommandParser implements Parser<StopCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StopCommand
     * and returns a StopCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StopCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new StopCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StopCommand.MESSAGE_USAGE), pe);
        }
    }

}
