package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.CommandTestUtil.getProjectAtIndex;
import static seedu.momentum.logic.commands.StopCommand.MESSAGE_USAGE;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.StopProjectCommand;
import seedu.momentum.logic.commands.StopTaskCommand;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;

class StopCommandParserTest {
    private StopCommandParser parser = new StopCommandParser();
    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    }

    @Test
    public void parse_stopProjectValidArgs_returnsStopProjectCommand() {
        assertParseSuccess(parser, "1", new StopProjectCommand(INDEX_FIRST), model);
    }

    @Test
    public void parse_stopProjectInvalidArgs_throwsParseException() {
        assertParseFailure(parser,
            "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), model);
    }

    @Test
    public void parse_stopProjectValidArgs_returnsStopTaskCommand() {
        Project project = getProjectAtIndex(model, INDEX_FIRST);
        model.viewTasks(project);
        assertParseSuccess(parser, "1", new StopTaskCommand(INDEX_FIRST, project), model);
    }

    @Test
    public void parse_stopTaskInvalidArgs_throwsParseException() {
        Project project = getProjectAtIndex(model, INDEX_FIRST);
        model.viewTasks(project);
        assertParseFailure(parser,
                "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), model);
    }
}
