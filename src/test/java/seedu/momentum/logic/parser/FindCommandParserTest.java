package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.parser.CliSyntax.FIND_TYPE;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.momentum.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.FindCommand;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.predicates.DescriptionContainsKeywordsPredicate;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.model.project.predicates.TagListContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();
    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), model);

        assertParseFailure(parser, " " + PREFIX_NAME + "         ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), model);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        // invalid match type
        assertParseFailure(parser, FIND_TYPE + "fail",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), model);

        // non empty preamble
        assertParseFailure(parser, "preamble " + PREFIX_NAME + "name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), model);

        // prefixes (other than /match) missing
        assertParseFailure(parser, FIND_TYPE + "all",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), model);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        List<String> keywords = Arrays.asList("first", "second");
        String userInput = " %sfirst second  " + FIND_TYPE + "all";

        // no leading and trailing whitespaces (name)
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(FindType.ALL, keywords));
        assertParseSuccess(parser, String.format(userInput, PREFIX_NAME), expectedFindCommand, model);

        // no leading and trailing whitespaces (desc)
        expectedFindCommand =
                new FindCommand(new DescriptionContainsKeywordsPredicate(FindType.ALL, keywords));
        assertParseSuccess(parser, String.format(userInput, PREFIX_DESCRIPTION), expectedFindCommand, model);

        // no leading and trailing whitespaces (tag)
        expectedFindCommand =
                new FindCommand(new TagListContainsKeywordsPredicate(FindType.ALL, keywords));
        assertParseSuccess(parser, String.format(userInput, PREFIX_TAG), expectedFindCommand, model);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_TAG + "\nfirst \nsecond " + FIND_TYPE + "\nall", expectedFindCommand, model);
    }

}
