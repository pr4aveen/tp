//@@author

package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;
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
 * Parses input arguments and creates an appropriate AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand and the current model,
     * and returns the corresponding AddCommand object for execution.
     *
     * @param args  Arguments to parse.
     * @param model The current model, to provide context for parsing the arguments.
     * @return A new add command with the parsed arguments.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public AddCommand parse(String args, Model model) throws ParseException {
        requireAllNonNull(args, model);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION,
                PREFIX_COMPLETION_STATUS, PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME, PREFIX_REMINDER, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = getParsedName(argMultimap);

        //@@author kkangs0226
        Description description = getParsedDescription(argMultimap);

        //@@author claracheong4
        CompletionStatus completionStatus = getParsedCompletionStatus(argMultimap);
        DateWrapper createdDateWrapper = getParsedCreatedDate();
        Deadline deadline = getParsedDeadline(argMultimap, createdDateWrapper);
        Reminder reminder = getParsedReminder(argMultimap);

        //@@author
        Set<Tag> tagList = getParsedTags(argMultimap);

        //@@author pr4aveen
        if (model.getViewMode() == ViewMode.PROJECTS) {
            return new AddProjectCommand(new Project(name, description, completionStatus, createdDateWrapper,
                    deadline, reminder, tagList));
        }
        return new AddTaskCommand(new Task(name, description, completionStatus, createdDateWrapper,
                deadline, reminder, tagList), model.getCurrentProject());
        //@@author
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given.
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The argument multimap.
     * @param prefixes         The prefixes.
     * @return The boolean.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    //@@author kkangs0226

    /**
     * Gets the parsed name.
     *
     * @param argMultimap The argument multimap of the addition.
     * @return The parsed name.
     * @throws ParseException If the name cannot be parsed.
     */
    private Name getParsedName(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap.getValue(PREFIX_NAME).isPresent();
        return ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
    }

    //@@author claracheong4

    /**
     * Gets the parsed description.
     *
     * @param argMultimap The argument multimap of the addition.
     * @return The parsed description.
     */
    private Description getParsedDescription(ArgumentMultimap argMultimap) {
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isEmpty()) {
            return Description.EMPTY_DESCRIPTION;
        }
        return ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
    }

    /**
     * Gets the parsed completion status.
     *
     * @param argMultimap The argument multimap of the addition.
     * @return The parsed completion status.
     */
    private CompletionStatus getParsedCompletionStatus(ArgumentMultimap argMultimap) {
        CompletionStatus completionStatus = new CompletionStatus();
        if (argMultimap.getValue(PREFIX_COMPLETION_STATUS).isPresent()) {
            return completionStatus.reverse();
        }
        return completionStatus;
    }

    /**
     * Gets the parsed created dates.
     *
     * @return The parsed created date.
     */
    private DateWrapper getParsedCreatedDate() {
        return Clock.now().getDateWrapper();
    }

    /**
     * Gets the parsed deadline.
     *
     * @param argMultimap        The argument multimap of the addition.
     * @param createdDateWrapper The created date wrapper.
     * @return The parsed deadline.
     * @throws ParseException If the deadline cannot be parsed.
     */
    private Deadline getParsedDeadline(ArgumentMultimap argMultimap, DateWrapper createdDateWrapper)
            throws ParseException {
        return ParserUtil.parseDeadline(
                argMultimap.getValue(PREFIX_DEADLINE_DATE),
                argMultimap.getValue(PREFIX_DEADLINE_TIME),
                createdDateWrapper);
    }

    //@@author

    /**
     * Gets the parsed reminder.
     *
     * @param argMultimap The argument multimap of the addition.
     * @return The parsed reminder.
     * @throws ParseException If the reminder cannot be parsed.
     */
    private Reminder getParsedReminder(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseReminder(argMultimap.getValue(PREFIX_REMINDER));
    }

    /**
     * Gets the parsed tags.
     *
     * @param argMultimap The argument multimap of the addition.
     * @return The parsed tags.
     * @throws ParseException If the tags cannot be parsed.
     */
    private Set<Tag> getParsedTags(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
    }

}
