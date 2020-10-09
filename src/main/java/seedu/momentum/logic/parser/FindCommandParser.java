package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.FindCommand.FILTER_ALL_MATCH;
import static seedu.momentum.logic.commands.FindCommand.FILTER_ANY_MATCH;
import static seedu.momentum.logic.parser.CliSyntax.FILTER_TYPE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.logic.commands.FindCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.project.DescriptionContainsKeywordsPredicate;
import seedu.momentum.model.project.NameContainsKeywordsPredicate;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TagListContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_TAG, FILTER_TYPE);

        List<Predicate<Project>> predicateList = new ArrayList<>();
        boolean isAllMatch = false;

        if (argMultimap.getValue(FILTER_TYPE).isPresent()) {
            String filterType = argMultimap.getValue(FILTER_TYPE).get();
            filterType = filterType.trim();

            switch (filterType) {
            case FILTER_ALL_MATCH:
                isAllMatch = true;
                break;
            case FILTER_ANY_MATCH:
                isAllMatch = false;
                break;
            default:
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> keywords = parseArguments(argMultimap, PREFIX_NAME);
            NameContainsKeywordsPredicate namePredicate =
                    new NameContainsKeywordsPredicate(isAllMatch, keywords);
            predicateList.add(namePredicate);
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            List<String> keywords = parseArguments(argMultimap, PREFIX_DESCRIPTION);
            DescriptionContainsKeywordsPredicate descriptionPredicate =
                    new DescriptionContainsKeywordsPredicate(isAllMatch, keywords);
            predicateList.add(descriptionPredicate);
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> keywords = parseArguments(argMultimap, PREFIX_TAG);
            TagListContainsKeywordsPredicate tagListPredicate =
                    new TagListContainsKeywordsPredicate(isAllMatch, keywords);
            predicateList.add(tagListPredicate);
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

    private List<String> parseArguments (ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        assert argMultimap.getValue(prefix).isPresent();
        String args = argMultimap.getValue(prefix).get();
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        return Arrays.asList(keywords);
    }
}
