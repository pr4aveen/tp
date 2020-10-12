package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.FindCommand.FIND_ALL_MATCH;
import static seedu.momentum.logic.commands.FindCommand.FIND_ANY_MATCH;
import static seedu.momentum.logic.parser.CliSyntax.FIND_TYPE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.logic.commands.FindCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.predicates.DescriptionContainsKeywordsPredicate;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.model.project.predicates.TagListContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String FIND_ARGUMENT_DELIMITER = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_TAG, FIND_TYPE);

        boolean isAllMatch = getMatchType(argMultimap); // only parses find type if it exists
        List<Prefix> prefixesToParse = Arrays.asList(PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_TAG);

        List<Predicate<Project>> predicateList = new ArrayList<>();

        for (Prefix prefix : prefixesToParse) {
            parseArguments(argMultimap, prefix, predicateList, isAllMatch);
        }

        if (predicateList.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(combinePredicates(isAllMatch, predicateList));
    }

    private Predicate<Project> combinePredicates(boolean isAllMatch, List<Predicate<Project>> predicateList) {

        Predicate<Project> combinedPredicate;

        if (isAllMatch) {
            combinedPredicate = predicateList.stream().reduce(Predicate::and).orElse(x -> true);
        } else {
            combinedPredicate = predicateList.stream().reduce(Predicate::or).orElse(x -> true);
        }
        return combinedPredicate;
    }

    private void parseArguments (ArgumentMultimap argMultimap, Prefix prefix,
             List<Predicate<Project>> predicateList, boolean isAllMatch) throws ParseException {

        if (argMultimap.getValue(prefix).isEmpty()) {
            return;
        }

        String args = argMultimap.getValue(prefix).get();
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> keywords = Arrays.asList(trimmedArgs.split(FIND_ARGUMENT_DELIMITER));

        if (prefix.equals(PREFIX_NAME)) {
            predicateList.add(new NameContainsKeywordsPredicate(isAllMatch, keywords));
        } else if (prefix.equals(PREFIX_DESCRIPTION)) {
            predicateList.add(new DescriptionContainsKeywordsPredicate(isAllMatch, keywords));
        } else if (prefix.equals(PREFIX_TAG)) {
            predicateList.add(new TagListContainsKeywordsPredicate(isAllMatch, keywords));
        }
    }

    private boolean getMatchType(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(FIND_TYPE).isEmpty()) {
            return false;
        }
        String filterType = argMultimap.getValue(FIND_TYPE).get();
        filterType = filterType.trim();

        switch (filterType) {
        case FIND_ALL_MATCH:
            return true;
        case FIND_ANY_MATCH:
            return false;
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
