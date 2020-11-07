//@@author pr4aveen

package seedu.momentum.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.momentum.logic.parser.CliSyntax.FIND_TYPE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_COMPLETION_STATUS;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.momentum.logic.commands.FindCommand;
import seedu.momentum.logic.parser.exceptions.ParseException;
import seedu.momentum.model.Model;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.predicates.CompletionStatusPredicate;
import seedu.momentum.model.project.predicates.DescriptionContainsKeywordsPredicate;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.MomentumPredicate;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.model.project.predicates.TagListContainsKeywordPredicate;

/**
 * Parses input arguments and creates an appropriate FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String FIND_ARGUMENT_DELIMITER = "\\s+";
    private static final String FIND_COMMAND_USAGE =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand,
     * and returns the corresponding FindCommand object for execution.
     *
     * @param args Arguments to parse.
     * @param model The current model, to provide context for parsing the arguments.
     * @return A new find command with the parsed arguments.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public FindCommand parse(String args, Model model) throws ParseException {
        requireAllNonNull(args, model);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
            args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_COMPLETION_STATUS, PREFIX_TAG, FIND_TYPE);

        Prefix[] prefixesToParse = new Prefix[] {PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_COMPLETION_STATUS, PREFIX_TAG};

        if (!argMultimap.getPreamble().isEmpty() || !anyPrefixPresent(argMultimap, prefixesToParse)) {
            throw new ParseException(FIND_COMMAND_USAGE);
        }

        FindType findType = getMatchType(argMultimap); // only parses find type if the argument exists.
        List<Predicate<TrackedItem>> predicateList = new ArrayList<>(); // list of all predicates that will be applied.

        for (Prefix prefix : prefixesToParse) {
            parseArguments(argMultimap, prefix, predicateList, findType);
        }

        return new FindCommand(combinePredicates(findType, predicateList));
    }

    /**
     * Combines predicates using predicate chaining.
     *
     * @param findType Find type of the search.
     * @param predicateList List of predicates to be combined.
     * @return A predicate that is the combination of all predicates in the predicate list.
     */
    private MomentumPredicate combinePredicates(FindType findType, List<MomentumPredicate> predicateList) {
        requireAllNonNull(findType, predicateList);
        BinaryOperator<MomentumPredicate> operationType;
        switch (findType) {
        case NONE:
            // Find none needs the logical 'and' of individual predicates.
        case ALL:
            operationType = Predicate::and;
            break;
        case ANY:
            // Find any is the default find type.
            // Fallthrough.
        default:
            operationType = Predicate::or;
            break;
        }

        return predicateList.stream().reduce(operationType).orElse(x -> true);
    }

    /**
     * Parses a given prefix and returns a predicate corresponding to that prefix.
     *
     * @param argMultimap Argument multimap used for parsing.
     * @param prefix Prefix that is being parsed.
     * @param predicateList List of predicates to add result to.
     * @param findType Find type used for the search.
     * @throws ParseException If the syntax is invalid.
     */
    private void parseArguments (ArgumentMultimap argMultimap, Prefix prefix,
                                 List<Predicate<TrackedItem>> predicateList, FindType findType) throws ParseException {

        requireAllNonNull(argMultimap, predicateList, predicateList, findType);

        if (argMultimap.getValue(prefix).isEmpty()) {
            return;
        }

        String args = argMultimap.getValue(prefix).get();
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(FIND_COMMAND_USAGE);
        }

        List<String> keywords = Arrays.asList(trimmedArgs.split(FIND_ARGUMENT_DELIMITER));
        assert !keywords.isEmpty() : "Keywords list cannot be empty";

        if (prefix.equals(PREFIX_NAME)) {
            predicateList.add(new NameContainsKeywordsPredicate(findType, keywords));
        } else if (prefix.equals(PREFIX_DESCRIPTION)) {
            predicateList.add(new DescriptionContainsKeywordsPredicate(findType, keywords));
        } else if (prefix.equals(PREFIX_COMPLETION_STATUS)) {
            if (!CompletionStatusPredicate.isValid(keywords)) {
                throw new ParseException(FIND_COMMAND_USAGE);
            }
            predicateList.add(new CompletionStatusPredicate(findType, keywords));
        } else if (prefix.equals(PREFIX_TAG)) {
            predicateList.add(new TagListContainsKeywordPredicate(findType, keywords));
        }
    }

    /**
     * Converts a match type argument into a {@code FindType} object.
     *
     * @param argMultimap Argument multimap used for parsing.
     * @return FindType enumeration corresponding to the argument.
     * @throws ParseException If the match type argument is invalid.
     */
    private FindType getMatchType(ArgumentMultimap argMultimap) throws ParseException {
        requireNonNull(argMultimap);
        if (argMultimap.getValue(FIND_TYPE).isEmpty()) {
            return FindType.ANY;
        }

        String findTypeArgument = argMultimap.getValue(FIND_TYPE).get();
        findTypeArgument = findTypeArgument.trim();

        try {
            return FindType.valueOf(findTypeArgument.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(FIND_COMMAND_USAGE);
        }

    }

    /**
     * Returns false if all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
