package seedu.momentum.logic.parser;

import static seedu.momentum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.momentum.logic.commands.CommandTestUtil.getProjectAtIndex;
import static seedu.momentum.logic.commands.StartCommand.MESSAGE_USAGE;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.momentum.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.StartProjectCommand;
import seedu.momentum.logic.commands.StartTaskCommand;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;

class StartCommandParserTest {
    private StartCommandParser parser = new StartCommandParser();
    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    }

    @Test
    public void parse_startProjectValidArgs_returnsStartCommand() {
        assertParseSuccess(parser, "1", new StartProjectCommand(INDEX_FIRST), model);
    }

    @Test
    public void parse_startProjectInvalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_USAGE), model);
    }

    @Test
    public void parse_startTaskValidArgs_returnsStartCommand() {
        Project project = getProjectAtIndex(model, INDEX_FIRST);
        model.viewTasks(project);
        assertParseSuccess(parser, "1", new StartTaskCommand(INDEX_FIRST, project), model);
    }

    @Test
    public void parse_startTaskInvalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_USAGE), model);
    }


}
