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

import java.util.Collection;
import java.util.Collections;

import seedu.momentum.commons.core.DateWrapper;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.logic.commands.EditCommand;
import seedu.momentum.logic.commands.EditProjectCommand;
import seedu.momentum.logic.commands.EditTaskCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ViewMode;
import seedu.momentum.model.project.CompletionStatus;

/**
 * Parses input arguments and creates an appropriate new EditCommand object.
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and current model,
     * and returns the corresponding EditCommand object for execution.
     *
     * @param model The current model, to provide context for parsing the arguments.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public EditCommand parse(String args, Model model) throws ParseException {
        requireAllNonNull(args, model);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION,
                PREFIX_COMPLETION_STATUS, PREFIX_DEADLINE_DATE, PREFIX_DEADLINE_TIME, PREFIX_REMINDER, PREFIX_TAG);

        Index index = getIndex(argMultimap);
        EditCommand.EditTrackedItemDescriptor editTrackedItemDescriptor = new EditCommand.EditTrackedItemDescriptor();

        setNameForEdit(argMultimap, editTrackedItemDescriptor);

        //@@author kkangs0226
        setDescriptionForEdit(argMultimap, editTrackedItemDescriptor);

        //@@author claracheong4
        setCompletionStatusForEdit(argMultimap, editTrackedItemDescriptor);
        setDeadlineForEdit(argMultimap, editTrackedItemDescriptor);
        setReminderForEdit(argMultimap, editTrackedItemDescriptor);

        //@@author
        setTagsForEdit(argMultimap, editTrackedItemDescriptor);

        if (!editTrackedItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        //@@author pr4aveen
        if (model.getViewMode() == ViewMode.PROJECTS) {
            return new EditProjectCommand(index, editTrackedItemDescriptor);
        }
        return new EditTaskCommand(index, editTrackedItemDescriptor, model.getCurrentProject());
        //@@author
    }

    /**
     * Gets the index of the tracked item edited.
     *
     * @param argMultimap The argument multimap of the edition.
     * @return The index.
     * @throws ParseException If the name cannot be parsed.
     */
    private Index getIndex(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Sets the name the edit descriptor after parsing it.
     *
     * @param argMultimap               The argument multimap of the edition.
     * @param editTrackedItemDescriptor The descriptor of the tracked item edited.
     * @throws ParseException If the name cannot be parsed.
     */
    private void setNameForEdit(ArgumentMultimap argMultimap,
                                EditCommand.EditTrackedItemDescriptor editTrackedItemDescriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editTrackedItemDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
    }

    //@@author kkangs0226

    /**
     * Sets the description the edit descriptor after parsing it.
     *
     * @param argMultimap               The argument multimap of the edition.
     * @param editTrackedItemDescriptor The descriptor of the tracked item edited.
     * @throws ParseException If the description cannot be parsed.
     */
    private void setDescriptionForEdit(ArgumentMultimap argMultimap,
                                       EditCommand.EditTrackedItemDescriptor editTrackedItemDescriptor) {
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editTrackedItemDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
    }

    //@@author claracheong4

    /**
     * Sets the completion status the edit descriptor after parsing it.
     *
     * @param argMultimap               The argument multimap of the edition.
     * @param editTrackedItemDescriptor The descriptor of the tracked item edited.
     */
    private void setCompletionStatusForEdit(ArgumentMultimap argMultimap,
                                            EditCommand.EditTrackedItemDescriptor editTrackedItemDescriptor) {
        if (argMultimap.getValue(PREFIX_COMPLETION_STATUS).isPresent()) {
            editTrackedItemDescriptor.setCompletionStatus(new CompletionStatus());
        }
    }

    /**
     * Sets the deadliine the edit descriptor after parsing it.
     *
     * @param argMultimap               The argument multimap of the edition.
     * @param editTrackedItemDescriptor The descriptor of the tracked item edited.
     * @throws ParseException If the deadline cannot be parsed.
     */
    private void setDeadlineForEdit(ArgumentMultimap argMultimap,
            EditCommand.EditTrackedItemDescriptor editTrackedItemDescriptor) throws ParseException {
        // use a default date to parse the deadline first
        // check whether if the deadline is after or on created date in edit command
        if (argMultimap.getValue(PREFIX_DEADLINE_DATE).isPresent()) {
            editTrackedItemDescriptor.setDeadline(ParserUtil.parseDeadline(
                    argMultimap.getValue(PREFIX_DEADLINE_DATE),
                    argMultimap.getValue(PREFIX_DEADLINE_TIME),
                    DateWrapper.MIN));
        }
    }

    /**
     * Sets the reminder the edit descriptor after parsing it.
     *
     * @param argMultimap               The argument multimap of the edition.
     * @param editTrackedItemDescriptor The descriptor of the tracked item edited.
     * @throws ParseException If the reminder cannot be parsed.
     */
    private void setReminderForEdit(ArgumentMultimap argMultimap,
            EditCommand.EditTrackedItemDescriptor editTrackedItemDescriptor) throws ParseException {
        if (argMultimap.getValue(PREFIX_REMINDER).isPresent()) {
            editTrackedItemDescriptor.setReminder(ParserUtil.parseReminder(
                    argMultimap.getValue(PREFIX_REMINDER)));
        }
    }

    //@@author
    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     * Sets the tags in the edit descriptor after parsing it.
     *
     * @param argMultimap               The argument multimap of the edition.
     * @param editTrackedItemDescriptor The descriptor of the tracked item edited.
     * @throws ParseException If the tags cannot be parsed.
     */
    private void setTagsForEdit(ArgumentMultimap argMultimap,
                                EditCommand.EditTrackedItemDescriptor editTrackedItemDescriptor) throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            Collection<String> tags = argMultimap.getAllValues(PREFIX_TAG);
            Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
            if (!tags.isEmpty()) {
                editTrackedItemDescriptor.setTags(ParserUtil.parseTags(tagSet));
            }
        }
    }

}
