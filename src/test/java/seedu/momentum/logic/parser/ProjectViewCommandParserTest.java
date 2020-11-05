package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.ProjectViewCommand.MESSAGE_USAGE;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.ProjectViewCommand;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;

class ProjectViewCommandParserTest {

    private ProjectViewCommandParser parser = new ProjectViewCommandParser();
    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new ProjectViewCommand(INDEX_FIRST), model);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), model);
    }

    @Test
    public void parse_validIndexFollowedByInvalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), model);
    }

}
