package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.StopProjectCommand;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;

class StopCommandParserTest {
    private StopCommandParser parser = new StopCommandParser();
    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void parse_validArgs_returnsStopCommand() {
        assertParseSuccess(parser, "1", new StopProjectCommand(INDEX_FIRST_PROJECT), model);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser,
            "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, StopProjectCommand.MESSAGE_USAGE), model);
    }
}
