package seedu.momentum.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.SortCommand.ASCENDING_ORDER;
import static seedu.momentum.logic.commands.SortCommand.DESCENDING_ORDER;
import static seedu.momentum.logic.parser.CliSyntax.SORT_ORDER;
import static seedu.momentum.logic.parser.CliSyntax.SORT_TYPE;

import seedu.momentum.logic.commands.SortCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.project.SortType;

public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user does not conform to the expected format.
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, SORT_TYPE, SORT_ORDER);

        boolean isAscending = parseSortOrder(argMultimap);
        SortType sortType = parseSortType(argMultimap);
        boolean isDefault = false;

        if (argMultimap.getValue(SORT_TYPE).isEmpty() && argMultimap.getValue(SORT_ORDER).isEmpty()) {
            isDefault = true;
            sortType = SortType.NULL;
        }

        return new SortCommand(sortType, isAscending, isDefault);
    }

    private boolean parseSortOrder(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(SORT_ORDER).isEmpty()) {
            return true;
        }

        String sortOrder = argMultimap.getValue(SORT_ORDER).get();
        sortOrder = sortOrder.trim();

        switch (sortOrder) {
        case ASCENDING_ORDER:
            return true;
        case DESCENDING_ORDER:
            return false;
        default:
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_INVALID_SORT_TYPE_OR_ORDER));
        }
    }

    private SortType parseSortType(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(SORT_TYPE).isEmpty()) {
            return SortType.NULL;
        }

        String sortType = argMultimap.getValue(SORT_TYPE).get();
        sortType = sortType.trim();

        switch (sortType) {
        case "alpha":
            return SortType.ALPHA;
        case "deadline":
            return SortType.DEADLINE;
        case "created":
            return SortType.CREATED;
        default:
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_INVALID_SORT_TYPE_OR_ORDER));
        }
    }

}
