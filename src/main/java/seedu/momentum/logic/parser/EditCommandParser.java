package seedu.momentum.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.EditCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.CompletionStatus;
import seedu.momentum.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args, Model model) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION,
                PREFIX_COMPLETION_STATUS, PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME, PREFIX_REMINDER, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditTrackedItemDescriptor editTrackedItemDescriptor = new EditCommand.EditTrackedItemDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editTrackedItemDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editTrackedItemDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_COMPLETION_STATUS).isPresent()) {
            editTrackedItemDescriptor.setCompletionStatus(new CompletionStatus());
        }

        // use a default date to parse the deadline first
        // check whether if the deadline is after or on created date in edit command
        if (argMultimap.getValue(PREFIX_DEADLINE_DATE).isPresent()) {
            editTrackedItemDescriptor.setDeadline(ParserUtil.parseDeadline(
                    argMultimap.getValue(PREFIX_DEADLINE_DATE),
                    argMultimap.getValue(PREFIX_DEADLINE_TIME),
                    DateWrapper.MIN));
        }

        // use a default date to parse the reminder first
        // check whether if the date of the reminder is after or on created date in edit command
        if (argMultimap.getValue(PREFIX_REMINDER).isPresent()) {
            editTrackedItemDescriptor.setReminder(ParserUtil.parseReminder(
                    argMultimap.getValue(PREFIX_REMINDER),
                    DateWrapper.MIN));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editTrackedItemDescriptor::setTags);

        if (!editTrackedItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        if (model.getViewMode() == ViewMode.PROJECTS) {
            return new EditCommand(index, editTrackedItemDescriptor);
        } else {
            return new EditCommand(index, editTrackedItemDescriptor, model.getCurrentProject());
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
