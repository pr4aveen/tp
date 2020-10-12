package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.StopCommand;

class StopCommandParserTest {
    private StopCommandParser parser = new StopCommandParser();

    @Test
    public void parse_validArgs_returnsStopCommand() {
        assertParseSuccess(parser, "1", new StopCommand(INDEX_FIRST_PROJECT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, StopCommand.MESSAGE_USAGE));
    }
}
