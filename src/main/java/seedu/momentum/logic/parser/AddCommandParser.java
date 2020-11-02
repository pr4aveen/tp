package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.logic.commands.AddCommand;
import seedu.momentum.logic.commands.AddProjectCommand;
import seedu.momentum.logic.commands.AddTaskCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.project.Deadline;
import seedu.momentum.model.project.Description;
import seedu.momentum.model.project.Name;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;
import seedu.momentum.model.reminder.Reminder;
import seedu.momentum.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @param model the current model.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args, Model model) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_COMPLETION_STATUS,
                        PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME, PREFIX_REMINDER, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Description description;
        if (!argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            description = Description.EMPTY_DESCRIPTION;
        } else {
            description = ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }

        CompletionStatus completionStatus = new CompletionStatus();
        if (argMultimap.getValue(PREFIX_COMPLETION_STATUS).isPresent()) {
            completionStatus = completionStatus.reverse();
        }

        DateWrapper createdDateWrapper = new DateWrapper(Clock.now().getDate());

        Deadline deadline = ParserUtil.parseDeadline(
                argMultimap.getValue(PREFIX_DEADLINE_DATE),
                argMultimap.getValue(PREFIX_DEADLINE_TIME),
                createdDateWrapper);

        Reminder reminder = ParserUtil.parseReminder(argMultimap.getValue(PREFIX_REMINDER), createdDateWrapper);

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (model.getViewMode() == ViewMode.PROJECTS) {
            return new AddProjectCommand(new Project(name, description, completionStatus, createdDateWrapper,
                    deadline, reminder, tagList));
        } else {
            return new AddTaskCommand(new Task(name, description, completionStatus, createdDateWrapper,
                    deadline, reminder, tagList), model.getCurrentProject());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
