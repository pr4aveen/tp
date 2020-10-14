package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_SORT_ORDER;
import static seedu.momentum.logic.commands.CommandTestUtil.INVALID_SORT_TYPE;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_ALPHA_SORT_TYPE;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_ASCENDING_SORT_ORDER;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_CREATED_DATE_SORT_TYPE;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DEADLINE_SORT_TYPE;
import static seedu.momentum.logic.commands.CommandTestUtil.VALID_DESCENDING_SORT_ORDER;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.SortCommand;
import seedu.momentum.model.project.SortType;

public class SortCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_INVALID_SORT_TYPE_OR_ORDER);

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_returnsDefaultSortCommand() {
        SortCommand defaultSortCommand = new SortCommand(SortType.NULL, true, true);
        assertParseSuccess(parser, "", defaultSortCommand);
    }

    @Test
    public void parse_missingSortType_returnsOrderedSortCommand() {

        // Ascending order
        SortCommand expectedCommand = new SortCommand(SortType.NULL, true, false);
        assertParseSuccess(parser, VALID_ASCENDING_SORT_ORDER, expectedCommand);

        // Descending order
        expectedCommand = new SortCommand(SortType.NULL, false, false);
        assertParseSuccess(parser, VALID_DESCENDING_SORT_ORDER, expectedCommand);

    }

    @Test
    public void parse_missingSortOrder_returnsTypedAscendingSortCommand() {

        // Alphabetical type
        SortCommand expectedCommand = new SortCommand(SortType.ALPHA, true, false);
        assertParseSuccess(parser, VALID_ALPHA_SORT_TYPE, expectedCommand);

        // Deadline type
        expectedCommand = new SortCommand(SortType.DEADLINE, true, false);
        assertParseSuccess(parser, VALID_DEADLINE_SORT_TYPE, expectedCommand);

        // Created date type
        expectedCommand = new SortCommand(SortType.CREATED, true, false);
        assertParseSuccess(parser, VALID_CREATED_DATE_SORT_TYPE, expectedCommand);

    }

    @Test
    public void parse_allFieldsSpecified_returnsSortCommand() {
        String userInput = VALID_ALPHA_SORT_TYPE + VALID_DESCENDING_SORT_ORDER;
        SortCommand expectedCommand = new SortCommand(SortType.ALPHA, false, false);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidSortType_failure() {
        assertParseFailure(parser, INVALID_SORT_TYPE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidSortOrder_failure() {
        assertParseFailure(parser, INVALID_SORT_ORDER, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {

    }

}
