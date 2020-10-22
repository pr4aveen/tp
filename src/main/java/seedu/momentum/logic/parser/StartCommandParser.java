package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.StartCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;

/**
 * Parses input arguments and creates a new StartCommand object
 */
public class StartCommandParser implements Parser<StartCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StartCommand
     * and returns a StartCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StartCommand parse(String args, Model model) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);

            if (model.getViewMode() == ViewMode.PROJECTS) {
                return new StartCommand(index);
            } else {
                return new StartCommand(index, model.getCurrentProject());
            }

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartCommand.MESSAGE_USAGE), pe);
        }
    }

}
