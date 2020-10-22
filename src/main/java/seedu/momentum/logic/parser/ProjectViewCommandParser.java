package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.ProjectViewCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;

/**
 * Parses input arguments and creates a new ProjectViewCommand object
 */
public class ProjectViewCommandParser implements Parser<ProjectViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProjectViewCommand
     * and returns a ProjectViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ProjectViewCommand parse(String args, Model model) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ProjectViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProjectViewCommand.MESSAGE_USAGE), pe);
        }
    }

}
