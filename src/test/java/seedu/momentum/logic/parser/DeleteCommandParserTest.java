package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.CommandTestUtil.getProjectAtIndex;
import static seedu.momentum.logic.commands.DeleteCommand.MESSAGE_USAGE;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.DeleteProjectCommand;
import seedu.momentum.logic.commands.DeleteTaskCommand;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();
    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @BeforeEach
    public void reset() {
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    }

    @Test
    public void parseProject_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteProjectCommand(INDEX_FIRST), model);
    }

    @Test
    public void parseProject_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), model);
    }

    @Test
    public void parseTask_validArgs_returnsDeleteCommand() {
        Project project = getProjectAtIndex(model, INDEX_FIRST);
        model.viewTasks(project);
        assertParseSuccess(parser, "2", new DeleteTaskCommand(INDEX_SECOND, project), model);
    }

    @Test
    public void parseTask_invalidArgs_throwsParseException() {
        Project project = getProjectAtIndex(model, INDEX_FIRST);
        model.viewTasks(project);
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), model);
    }
}
