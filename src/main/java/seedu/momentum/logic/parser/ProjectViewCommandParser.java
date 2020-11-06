//@@author boundtotheearth
package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.ProjectViewCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;

/**
 * Parses input arguments and creates a new ProjectViewCommand object.
 */
public class ProjectViewCommandParser implements Parser<ProjectViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ProjectViewCommand
     * and returns a ProjectViewCommand object for execution.
     *
     * @param args Arguments to parse.
     * @param model The current model, to provide context for parsing the arguments.
     * @return A new project view command with the parsed arguments.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public ProjectViewCommand parse(String args, Model model) throws ParseException {
        requireAllNonNull(args, model);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ProjectViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ProjectViewCommand.MESSAGE_USAGE), pe);
        }
    }

}
