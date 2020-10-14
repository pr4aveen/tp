package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_SORT_ORDER;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_SORT_TYPE;
import static seedu.momentum.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_ALPHA_SORT_TYPE;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_ASCENDING_SORT_ORDER;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_CREATED_DATE_SORT_TYPE;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_SORT_TYPE;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCENDING_SORT_ORDER;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.SortCommandUtil.ALPHA_ASCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.ALPHA_DESCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.CREATED_DATE_ASCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.DEADLINE_ASCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.DEFAULT_SORT_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.SortCommand;

public class SortCommandParserTest {

    private static final String MESSAGE_INVALID_SORT_TYPE_OR_ORDER = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_INVALID_SORT_TYPE_OR_ORDER);
    private static final String MESSAGE_NON_EMPTY_PREAMBLE_FAILURE = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_returnsDefaultSortCommand() {
        assertParseSuccess(parser, " ", DEFAULT_SORT_COMMAND);
    }

    @Test
    public void parse_missingSortType_returnsOrderedSortCommand() {

        // Ascending order
        assertParseSuccess(parser, VALID_ASCENDING_SORT_ORDER, NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND);

        // Descending order
        assertParseSuccess(parser, VALID_DESCENDING_SORT_ORDER, NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND);

    }

    @Test
    public void parse_missingSortOrder_returnsTypedAscendingSortCommand() {

        // Alphabetical type
        assertParseSuccess(parser, VALID_ALPHA_SORT_TYPE, ALPHA_ASCENDING_COMMAND);

        // Deadline type
        assertParseSuccess(parser, VALID_DEADLINE_SORT_TYPE, DEADLINE_ASCENDING_COMMAND);

        // Created date type
        assertParseSuccess(parser, VALID_CREATED_DATE_SORT_TYPE, CREATED_DATE_ASCENDING_COMMAND);

    }

    @Test
    public void parse_allFieldsSpecified_returnsSortCommand() {
        String userInput = VALID_ALPHA_SORT_TYPE + VALID_DESCENDING_SORT_ORDER;
        assertParseSuccess(parser, userInput, ALPHA_DESCENDING_COMMAND);
    }

    @Test
    public void parse_invalidSortType_failure() {
        assertParseFailure(parser, INVALID_SORT_TYPE, MESSAGE_INVALID_SORT_TYPE_OR_ORDER);
    }

    @Test
    public void parse_invalidSortOrder_failure() {
        assertParseFailure(parser, INVALID_SORT_ORDER, MESSAGE_INVALID_SORT_TYPE_OR_ORDER);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = PREAMBLE_NON_EMPTY + VALID_ALPHA_SORT_TYPE + VALID_ASCENDING_SORT_ORDER;
        assertParseFailure(parser, userInput, MESSAGE_NON_EMPTY_PREAMBLE_FAILURE);
    }

}
